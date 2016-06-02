package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/images")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @ResponseBody
    @RequestMapping(value= "/welcome", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] welcome() throws IOException {
        return imageService.getWelcomeImage();
    }
}
