package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.view.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/tags")
@CrossOrigin
public class TagController {

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<TagDto> allTags() {
        return tagService.findAll();
    }

}
