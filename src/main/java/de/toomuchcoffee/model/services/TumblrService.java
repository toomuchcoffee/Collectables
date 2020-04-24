package de.toomuchcoffee.model.services;

import com.google.common.collect.ImmutableMap;
import com.tumblr.jumblr.JumblrClient;
import com.tumblr.jumblr.types.Blog;
import com.tumblr.jumblr.types.Photo;
import com.tumblr.jumblr.types.PhotoPost;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import static com.google.common.collect.Sets.intersection;
import static com.google.common.collect.Sets.newHashSet;
import static java.util.stream.Collectors.toList;

@Service
public class TumblrService {
    private static final int MAX_PAGE_SIZE = 50;

    private List<TumblrPost> posts = new ArrayList<>();

    private Long timestamp;

    private final JumblrClient jumblrClient;

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 8);

    private TumblrService(
            @Value("${tumblr.consumer-key}") String consumerKey,
            @Value("${tumblr.consumer-secret}") String consumerSecret) {
        this.jumblrClient = new JumblrClient(consumerKey, consumerSecret);
    }


    public List<TumblrPost> getPosts(Set<String> filter) {
        return posts.stream()
                .filter(tp -> tp.tags != null && intersection(filter, newHashSet(tp.tags)).size() > 0)
                .collect(toList());
    }

    public TumblrPost getAnyPost() {
        int index = new Random().nextInt(posts.size());
        return posts.get(index);
    }

    @PostConstruct
    private void initialize() throws IOException {
        readPosts();
    }

    public void readPosts() {
        Blog blog = jumblrClient.blogInfo("yaswb.tumblr.com");
        Integer postCount = blog.getPostCount();

        BiConsumer<Integer, Integer> function = (offset, pageSize) -> {
            Map<String, Integer> options = ImmutableMap.of("limit", pageSize, "offset", offset);
            executor.submit(() ->
                    jumblrClient.blogPosts("yaswb", options).stream()
                            .filter(post -> post instanceof PhotoPost)
                            .map(post -> (PhotoPost) post)
                            .forEach(post -> post.getPhotos().stream()
                                    .map(photo -> mapToTumblrResponse(post, photo))
                                    .forEach(posts::add)));
        };

        new BatchedExecutor(MAX_PAGE_SIZE, postCount).execute(function);
    }

    public Long getTimestamp() {
        return timestamp;
    }

    private TumblrPost mapToTumblrResponse(PhotoPost post, Photo photo) {
        TumblrPost tumblrPost = new TumblrPost(post.getTags().toArray(new String[0]));
        photo.getSizes().forEach(s -> {
            switch (s.getWidth()) {
                case 1280:
                    tumblrPost.setPhotoUrl1280(s.getUrl());
                    break;
                case 500:
                    tumblrPost.setPhotoUrl500(s.getUrl());
                    break;
                case 400:
                    tumblrPost.setPhotoUrl400(s.getUrl());
                    break;
                case 250:
                    tumblrPost.setPhotoUrl250(s.getUrl());
                    break;
                case 100:
                    tumblrPost.setPhotoUrl100(s.getUrl());
                    break;
                case 75:
                    tumblrPost.setPhotoUrl75(s.getUrl());
                    break;
            }
        });
        return tumblrPost;
    }


    @Getter
    @Setter
    @RequiredArgsConstructor
    public static class TumblrPost {
        public final String[] tags;

        public String photoUrl1280;

        public String photoUrl500;

        public String photoUrl400;

        public String photoUrl250;

        public String photoUrl100;

        public String photoUrl75;
    }

    @RequiredArgsConstructor
    public static class BatchedExecutor {
        private final int pageSize;
        private final int totalCount;

        public <T> void execute(BiConsumer<Integer, Integer> function) {
            int count = 0;
            while ((count * pageSize) < totalCount) {
                int offset = count * pageSize;
                function.accept(offset, pageSize);
                count++;
            }
        }
    }

}
