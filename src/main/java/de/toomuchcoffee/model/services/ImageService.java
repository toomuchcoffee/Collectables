package de.toomuchcoffee.model.services;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Tag;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ImageService {

    public static final String REGEX_EXCLUDING_WHITESPACE = "[^a-zA-Z0-9]";
    public static final String REGEX_INCLUDING_WHITESPACE = "[^a-zA-Z0-9 ]";

    public byte[] getWelcomeImage() throws IOException {
        List<TumblrPost> tumblrPosts = getTumblrPosts("starwars", true);
        String url = tumblrPosts.get(0).photoUrl1280;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    public byte[] getCollectibleThumbnail(Collectible collectible) {
        List<String> tags;
        try {
            List<TumblrPost> tumblrPosts;
            try {
                tags = Arrays.stream(collectible.getVerbatim().split("\\(", 2))
                        .map(t -> t.replaceAll(REGEX_EXCLUDING_WHITESPACE, "").trim().toLowerCase())
                        .collect(Collectors.toList());
                tumblrPosts = getTumblrPosts(tags.get(0), false);
            } catch (HttpClientErrorException ex) {
                if (ex.getStatusCode().value() == 404) {
                    tags = Arrays.stream(collectible.getVerbatim().split("\\(", 2))
                            .map(t -> t.replaceAll(REGEX_INCLUDING_WHITESPACE, "").trim().toLowerCase())
                            .collect(Collectors.toList());
                    tumblrPosts = getTumblrPosts(tags.get(0), false);
                } else {
                    throw ex;
                }
            }
            Set<String> queryTags = Sets.newHashSet(tags);
            queryTags.add(collectible.getProductLine().getAbbreviation());
            queryTags.addAll(collectible.getTags().stream().map(Tag::getName).collect(Collectors.toSet()));

            for (int threshold = 7; threshold > 0; threshold--) {
                for (TumblrPost post : tumblrPosts) {
                    Set<String> itemTags = Arrays.stream(post.tags).collect(Collectors.toSet());
                    if (Sets.intersection(queryTags, itemTags).size() >= threshold) {
                        String url = post.photoUrl75;
                        return new RestTemplate().getForObject(url, byte[].class);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    private List<TumblrPost> getTumblrPosts(String tag, boolean limit) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        List<TumblrPost> tumblrPosts = Lists.newArrayList();

        boolean stop = limit;
        int page = 1;
        do {
            String tumblrUrl;
            if (page == 1)
                tumblrUrl = String.format("http://yaswb.tumblr.com/tagged/%s/json", tag);
            else
                tumblrUrl = String.format("http://yaswb.tumblr.com/tagged/%s/page/%d/json", tag, page);

            String jsonString = new RestTemplate().getForObject(tumblrUrl, String.class);
            jsonString = jsonString.replaceFirst("var tumblr_api_read =", "").trim();

            TumblrResponse tumblrResponse = objectMapper.readValue(jsonString, TumblrResponse.class);
            if (tumblrResponse.posts.length == 0) {
                stop = true;
            } else {
                tumblrPosts.addAll(Arrays.asList(tumblrResponse.posts));
                page++;
            }
        } while (!stop);

        return tumblrPosts;
    }

    @JsonIgnoreProperties
    public static class TumblrResponse {
        public TumblrPost[] posts;
    }

    @JsonIgnoreProperties
    public static class TumblrPost {
        public String[] tags;

        @JsonProperty("photo-url-1280")
        public String photoUrl1280;

        @JsonProperty("photo-url-75")
        public String photoUrl75;

        // photo-url-500
        // photo-url-400
        // photo-url-250
        // photo-url-100
    }

}
