package de.toomuchcoffee.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.toomuchcoffee.entites.Collectible;
import de.toomuchcoffee.entites.Tag;
import de.toomuchcoffee.repositories.CollectibleRepository;
import de.toomuchcoffee.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gerald.sander on 04/04/16.
 */
@Controller
@RequestMapping("/")
public class CollectibleController {

    @Autowired
    private CollectibleRepository collectibleRepository;

    @Autowired
    private TagRepository tagRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allCollectibles(Model model) {
        List<Collectible> collectibles = collectibleRepository.findAll();
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addToCollectibles(Collectible collectible) {
        collectibleRepository.save(collectible);
        return "redirect:/";
    }

    @RequestMapping(value = "{tagName}", method = RequestMethod.GET)
    public String collectiblesByTag(@PathVariable String tagName, Model model) {
        Tag tag = tagRepository.findOne(tagName);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }
}
