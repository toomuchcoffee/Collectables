package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class ImageService {

    @Autowired
    private TumblrService tumblrService;

    @Autowired
    private TagService tagService;

    public byte[] getWelcomeImage() throws IOException {
        String url = tumblrService.getAnyPost().photoUrl500;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        // gather tags
        Set<String> verbatimTags = tagService.getVerbatimPermutations(collectible.getVerbatim());

        Set<String> tagQuery = Sets.newHashSet(verbatimTags);
        tagQuery.add(collectible.getProductLine().getCode());

        // filter posts by tags
        List<RankedPost> rankedPosts = tumblrService.getPosts().stream()
                .filter(tp -> Sets.intersection(verbatimTags, Sets.newHashSet(tp.tags)).size() > 0)
                .map(tp -> new RankedPost(
                        Sets.intersection(tagQuery, Sets.newHashSet(tp.tags)).size(),
                        Sets.difference(Sets.newHashSet(tp.tags), tagQuery).size(),
                        tp))
                .sorted(Comparator.comparing(RankedPost::getMisses))
                .sorted(Collections.reverseOrder(Comparator.comparing(RankedPost::getHits)))
                .collect(toList());

        // get the image
        if (rankedPosts.size() > 0) {
            String url = rankedPosts.get(0).getPost().photoUrl75;
            return new RestTemplate().getForObject(url, byte[].class);
        }
        return new byte[0];
    }

    private class RankedPost {
        private int hits;
        private int misses;
        private TumblrPost post;

        public RankedPost(int hits, int misses, TumblrPost post) {
            this.hits = hits;
            this.misses = misses;
            this.post = post;
        }

        public int getHits() {
            return hits;
        }

        public int getMisses() {
            return misses;
        }

        public TumblrPost getPost() {
            return post;
        }
    }

}
