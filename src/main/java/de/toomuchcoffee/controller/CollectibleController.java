package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.CollectibleService;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/collectibles")
@CrossOrigin
public class CollectibleController {

    @Autowired
    private CollectibleService collectibleService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<CollectibleDto> allCollectibles() {
        return collectibleService.find();
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addToCollectibles(@RequestBody CollectibleDto collectible) {
        collectibleService.add(collectible);
    }

    @RequestMapping(value = "tag/{tagName}", method = RequestMethod.GET)
    public List<CollectibleDto> collectiblesByTag(@PathVariable String tagName) {
        return collectibleService.findByTagName(tagName);
    }

    @RequestMapping(value = "line/{productLineName}", method = RequestMethod.GET)
    public List<CollectibleDto> collectiblesByLine(@PathVariable String productLineName) {
        return collectibleService.findByProductLineName(productLineName);
    }
}
