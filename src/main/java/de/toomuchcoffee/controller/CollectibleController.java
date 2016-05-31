package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.CollectibleService;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/")
@CrossOrigin
public class CollectibleController {

    @Autowired
    private CollectibleService collectibleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CollectibleDto> allCollectibles(Model model) {
        return collectibleService.find();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addToCollectibles(@RequestBody CollectibleDto collectible) {
        collectibleService.add(collectible);
    }

    @RequestMapping(value = "tag/{tagName}", method = RequestMethod.GET)
    public List<CollectibleDto> collectiblesByTag(@PathVariable String tagName, Model model) {
        return collectibleService.findByTagName(tagName);
    }

    @RequestMapping(value = "line/{productLineName}", method = RequestMethod.GET)
    public List<CollectibleDto> collectiblesByLine(@PathVariable String productLineName, Model model) {
        return collectibleService.findByProductLineName(productLineName);
    }
}
