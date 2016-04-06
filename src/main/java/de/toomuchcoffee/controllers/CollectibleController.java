package de.toomuchcoffee.controllers;

import de.toomuchcoffee.dtos.CollectibleDto;
import de.toomuchcoffee.service.CollectibleService;
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
    private CollectibleService collectibleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allCollectibles(Model model) {
        List<CollectibleDto> collectibles = collectibleService.find();
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String addToCollectibles(CollectibleDto collectible) {
        collectibleService.add(collectible);
        return "redirect:/";
    }

    @RequestMapping(value = "tag/{tagName}", method = RequestMethod.GET)
    public String collectiblesByTag(@PathVariable String tagName, Model model) {
        List<CollectibleDto> collectibles = collectibleService.findByTagName(tagName);
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }
}
