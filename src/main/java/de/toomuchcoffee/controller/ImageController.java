package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping(value = "/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @ResponseBody
    @RequestMapping(value= "/welcome", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] welcome() throws IOException {
        return imageService.getWelcomeImage();
    }
}
