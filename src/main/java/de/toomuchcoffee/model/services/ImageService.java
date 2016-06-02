package de.toomuchcoffee.model.services;

import com.jayway.jsonpath.JsonPath;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class ImageService {

    public static final String IMAGE_BASE_URL = "http://yaswb.tumblr.com/api/read/json?callback=?";

    public byte[] getWelcomeImage() throws IOException {
        List<Map<String, String>> tumblrPosts = getTumblrPosts();
        int nextInt = new Random().nextInt(tumblrPosts.size() - 1);
        String url = tumblrPosts.get(nextInt).get("photo-url-1280");;
        return new RestTemplate().getForObject(url, byte[].class);
    }

    private List<Map<String, String>> getTumblrPosts() {
        String jsonString = new RestTemplate().getForObject(IMAGE_BASE_URL, String.class)
                .replaceFirst("var tumblr_api_read = ", "");
        JsonPath jsonPath = JsonPath.compile("$.posts");
        return jsonPath.read(jsonString);
    }


/*
*
* {
    "tumblelog": {
        "title": "YASWB - Yet Another Star Wars Blog",
        "description": "                                                                                                                                                                                                                A retrospection on Vintage Star Wars with modern action figures and other Star Wars toys.\nWith the help of a cheap camera, a few colored cardboards, a Swedish halogen desk light, insomniac tendencies, and a growing collection of Star Wars plastic.",
        "name": "yaswb",
        "timezone": "Europe\/Amsterdam",
        "cname": false,
        "feeds": []
    },
    "posts-start": 0,
    "posts-total": 1426,
    "posts-type": false,
    "posts": [
        {
            "id": "145296385874",
            "url": "http:\/\/yaswb.tumblr.com\/post\/145296385874",
            "url-with-slug": "http:\/\/yaswb.tumblr.com\/post\/145296385874\/the-first-boba-fett-in-my-collection-that-can-pose",
            "type": "photo",
            "date-gmt": "2016-06-02 07:16:31 GMT",
            "date": "Thu, 02 Jun 2016 09:16:31",
            "bookmarklet": 0,
            "mobile": 0,
            "feed-item": "",
            "from-feed-id": 0,
            "unix-timestamp": 1464851791,
            "format": "html",
            "reblog-key": "IQ5vZweS",
            "slug": "the-first-boba-fett-in-my-collection-that-can-pose",
            "photo-caption": "<p>The first Boba Fett in my collection that can pose like on the original vintage Kenner cardback #starwars #actionfigures #bobafett #bandai #kenner #bountyhunter<\/p>",
            "photo-link-url": "https:\/\/www.instagram.com\/p\/BGJLTFSlzY0\/",
            "width": 1080,
            "height": 1080,
            "photo-url-1280": "http:\/\/66.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_1280.jpg",
            "photo-url-500": "http:\/\/67.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_500.jpg",
            "photo-url-400": "http:\/\/66.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_400.jpg",
            "photo-url-250": "http:\/\/66.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_250.jpg",
            "photo-url-100": "http:\/\/66.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_100.jpg",
            "photo-url-75": "http:\/\/66.media.tumblr.com\/e5a7acec5c9abf75c2624544dfce3627\/tumblr_o84uvjeOzd1ssj8pjo1_75sq.jpg",
            "photos": [],
            "tags": [
                "starwars",
                "kenner",
                "bobafett",
                "bountyhunter",
                "actionfigures",
                "bandai"
            ]
        },
*
*       ...
*  }
*
*
*
* */


}
