package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.view.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@CrossOrigin
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<TagDto> allTags() {
        return tagService.findAll();
    }

}
