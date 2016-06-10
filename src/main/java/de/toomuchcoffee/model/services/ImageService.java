package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        Pair<Set<String>, Set<String>> verbatimTags = tagService.getVerbatimTags(collectible.getVerbatim());

        Set<String> verbatimQuery = verbatimTags.getLeft();
        Set<String> tagQuery = Sets.newHashSet();
        tagQuery.addAll(verbatimTags.getLeft());
        tagQuery.addAll(verbatimTags.getRight());

        tagQuery.add(collectible.getProductLine().getAbbreviation());
        tagQuery.addAll(collectible.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

        // filter posts by tags
        List<Pair<TumblrPost, Integer>> rankedPosts = tumblrService.getPosts().stream()
                .filter(tp -> Sets.intersection(verbatimQuery, Sets.newHashSet(tp.tags)).size() > 0)
                .map(tp -> Pair.of(tp, Sets.intersection(tagQuery, Sets.newHashSet(tp.tags)).size()))
                .sorted(Collections.reverseOrder(Comparator.comparing(Pair::getRight)))
                .collect(Collectors.toList());

        // get the image
        if (rankedPosts.size() > 0) {
            String url = rankedPosts.get(0).getLeft().photoUrl75;
            return new RestTemplate().getForObject(url, byte[].class);
        }
        return new byte[0];
    }

}
