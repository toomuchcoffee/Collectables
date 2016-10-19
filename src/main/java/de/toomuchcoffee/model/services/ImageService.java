package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.services.RankingService.RankedPost;
import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class ImageService {

    @Autowired
    private TumblrService tumblrService;

    @Autowired
    private PermutationService permutationService;

    @Autowired
    private RankingService rankingService;

    public byte[] getWelcomeImage() throws IOException {
        String url = tumblrService.getAnyPost().photoUrl500;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        Set<String> verbatimTags = permutationService.getPermutations(collectible.getVerbatim());

        Set<String> tagQuery = Sets.newHashSet(verbatimTags);
        tagQuery.add(collectible.getProductLine().getCode());

        List<TumblrPost> posts = tumblrService.getPosts().stream()
                .filter(tp -> Sets.intersection(verbatimTags, Sets.newHashSet(tp.tags)).size() > 0).collect(toList());

        List<RankedPost> rankedPosts = rankingService.getRankedPosts(posts, tagQuery);

        if (rankedPosts.size() > 0) {
            String url = rankedPosts.get(0).getPost().photoUrl75;
            return new RestTemplate().getForObject(url, byte[].class);
        }
        return new byte[0];
    }

}
