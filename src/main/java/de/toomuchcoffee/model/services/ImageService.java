package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.services.RankingService.RankedPost;
import de.toomuchcoffee.model.services.TumblrService.TumblrPost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final TumblrService tumblrService;

    private final PermutationService permutationService;

    private final RankingService rankingService;

    public byte[] getWelcomeImage() throws IOException {
        String url = tumblrService.getAnyPost().photoUrl500;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        Set<String> filter = permutationService.getPermutations(collectible.getVerbatim());

        Set<String> qualifiers = Sets.newHashSet(filter);
        qualifiers.add(collectible.getProductLine().name());
        qualifiers.addAll(collectible.getProductLine().getTags());
        qualifiers.addAll(permutationService.getPermutations(collectible.getProductLine().getVerbatim()));

        List<TumblrPost> posts = tumblrService.getPosts(filter);

        List<RankedPost> rankedPosts = rankingService.getRankedPosts(posts, qualifiers);

        if (rankedPosts.size() > 0) {
            String url = rankedPosts.get(0).getPost().photoUrl75;
            return new RestTemplate().getForObject(url, byte[].class);
        }
        return new byte[0];
    }

}
