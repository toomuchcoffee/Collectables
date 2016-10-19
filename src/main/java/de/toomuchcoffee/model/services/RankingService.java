package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class RankingService {

    public List<RankedPost> getRankedPosts(List<TumblrPost> posts, Set<String> tagQuery) {
        return posts.stream()
                .map(tp -> new RankedPost(
                        Sets.intersection(tagQuery, Sets.newHashSet(tp.tags)).size(),
                        Sets.difference(Sets.newHashSet(tp.tags), tagQuery).size(),
                        tp))
                .sorted(Comparator.comparing(RankedPost::getMisses))
                .sorted(Collections.reverseOrder(Comparator.comparing(RankedPost::getHits)))
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
