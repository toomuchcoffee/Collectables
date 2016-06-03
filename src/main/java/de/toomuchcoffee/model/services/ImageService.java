package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import com.jayway.jsonpath.JsonPath;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImageService {

    @Value("${de.toomuchcoffee.collectables.tumblr-api-key}")
    private String tumblrApiKey;

    public byte[] getWelcomeImage() throws IOException {
        JsonPath jsonPath = JsonPath.compile("$.response.posts[0].photos[0].original_size.url");
        TumblrResponseWrapper tumblrResponse = getTumblrResponse("starwars");
        String url = tumblrResponse.response.posts[0].photos[0].original_size.url;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        String tag = collectible.getVerbatim().replaceAll("[^a-zA-Z0-9]", "").trim().toLowerCase();
        try {
            TumblrResponseWrapper tumblrResponse = getTumblrResponse(tag);

            Set<String> queryTags = Sets.newHashSet(tag);
            queryTags.add(collectible.getProductLine().getAbbreviation());
            queryTags.addAll(collectible.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            for (TumblrPost post : tumblrResponse.response.posts) {
                for (int threshold = 5; threshold > 0; threshold--) {
                    Set<String> itemTags = Arrays.stream(post.tags).collect(Collectors.toSet());
                    if (Sets.intersection(queryTags, itemTags).size() > threshold) {
                        String url = post.photos[0].alt_sizes[5].url;
                        return new RestTemplate().getForObject(url, byte[].class);
                    }
                }
            }
        } catch (Exception e){
            // TODO handle exception
        }
        return new byte[0];
    }

    private TumblrResponseWrapper getTumblrResponse(String tag) {
        String tumblrUrl = "https://api.tumblr.com/v2/blog/yaswb.tumblr.com/posts/photo?api_key=" + tumblrApiKey + "&tag=" + tag;
        return new RestTemplate().getForObject(tumblrUrl, TumblrResponseWrapper.class);
    }

    public static class TumblrResponseWrapper {
        public TumblrResponse response;
        public TumblrResponseWrapper() {
        }
    }

    public static class TumblrResponse {
        public TumblrPost[] posts;

        public TumblrResponse() {
        }
    }

    public static class TumblrPost {
        public String[] tags;
        public TumblrPhoto[] photos;

        public TumblrPost() {
        }
    }

    public static class TumblrPhoto {
        public String caption;
        public TumblrPhotoSource[] alt_sizes;
        public TumblrPhotoSource original_size;

        public TumblrPhoto() {
        }
    }

    public static class TumblrPhotoSource {
        public String url;
        public Integer width;
        public Integer height;
    }

}
