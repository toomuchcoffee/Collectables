package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tags")
@CrossOrigin
public class TagController {

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<String> allTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream().map(Tag::getName).collect(Collectors.toList());
    }

}
