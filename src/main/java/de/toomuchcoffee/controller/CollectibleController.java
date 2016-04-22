package de.toomuchcoffee.controller;

import de.toomuchcoffee.view.CollectibleDto;
import de.toomuchcoffee.model.services.CollectibleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

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

    @RequestMapping(value = "line/{productLineName}", method = RequestMethod.GET)
    public String collectiblesByLine(@PathVariable String productLineName, Model model) {
        List<CollectibleDto> collectibles = collectibleService.findByProductLineName(productLineName);
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }
}
