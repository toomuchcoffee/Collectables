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
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ImageService {

    public static final String REGEX_EXCLUDING_WHITESPACE = "[^a-zA-Z0-9]";
    public static final String REGEX_INCLUDING_WHITESPACE = "[^a-zA-Z0-9 ]";

    @Autowired
    private TumblrService tumblrService;

    public byte[] getWelcomeImage() throws IOException {
        String url = tumblrService.getAnyPost().photoUrl500;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        // gather tags
        String[] verbatimTerms = collectible.getVerbatim().toLowerCase().split("\\(", 2);

        String verbatimNoWhitespace = verbatimTerms[0].replaceAll(REGEX_EXCLUDING_WHITESPACE, "").trim();
        String verbatimWithWhitespace = verbatimTerms[0].replaceAll(REGEX_INCLUDING_WHITESPACE, "").trim();

        HashSet<String> verbatimQuery = Sets.newHashSet();
        Set<String> tagQuery = Sets.newHashSet();

        verbatimQuery.add(verbatimNoWhitespace);
        verbatimQuery.add(verbatimWithWhitespace);

        tagQuery.add(verbatimNoWhitespace);
        tagQuery.add(verbatimWithWhitespace);

        if (verbatimTerms.length > 1) {
            tagQuery.add(verbatimTerms[1].replaceAll(REGEX_EXCLUDING_WHITESPACE, "").trim());
            tagQuery.add(verbatimTerms[1].replaceAll(REGEX_INCLUDING_WHITESPACE, "").trim());
        }
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
