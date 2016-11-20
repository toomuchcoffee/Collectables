package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.google.common.collect.Sets.*;
import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
public class RankingService {

    public List<RankedPost> getRankedPosts(List<TumblrPost> posts, Set<String> qualifiers) {
        return posts.stream()
                .map(tp -> new RankedPost(
                        intersection(qualifiers, newHashSet(tp.tags)).size(),
                        difference(newHashSet(tp.tags), qualifiers).size(),
                        tp))
                .sorted(comparing(RankedPost::getMisses))
                .sorted(reverseOrder(comparing(RankedPost::getHits)))
                .collect(toList());
    }

    public static class RankedPost {
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
