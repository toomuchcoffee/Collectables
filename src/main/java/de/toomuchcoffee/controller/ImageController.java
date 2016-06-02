package de.toomuchcoffee.controller;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    public static final String IMAGE_BASE_URL = "http://yaswb.tumblr.com/api/read/json?callback=?";

    @ResponseBody
    @RequestMapping(value= "/welcome", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] welcome() throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        String jsonString = restTemplate.getForObject(IMAGE_BASE_URL, String.class)
                .replaceFirst("var tumblr_api_read = ", "");

        JsonPath jsonPath = JsonPath.compile("$.posts[0].photo-url-1280");
        String url = jsonPath.read(jsonString);

        return restTemplate.getForObject(url, byte[].class);
    }
}
