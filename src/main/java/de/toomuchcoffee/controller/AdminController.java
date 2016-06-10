package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.CollectibleService;
import de.toomuchcoffee.model.services.ProductLineService;
import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private CollectibleService collectibleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ProductLineService productLineService;

    @RequestMapping(value = "/collectibles", method = RequestMethod.POST)
    public void addToCollectibles(@RequestBody CollectibleDto collectible) {
        collectibleService.add(collectible);
    }

    @RequestMapping(value = "/collectibles/{id}", method = RequestMethod.PUT)
    public void modifyCollectible(@PathVariable Long id, @RequestBody CollectibleDto collectible) {
        collectibleService.update(id, collectible);
    }

    @RequestMapping(value = "/collectibles/{id}", method = RequestMethod.DELETE)
    public void deleteFromCollectibles(@PathVariable Long id) {
        collectibleService.delete(id);
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    public void deleteFromTags(@PathVariable String id) {
        tagService.delete(id);
    }

    @RequestMapping(value = "/lines/{id}", method = RequestMethod.DELETE)
    public void deleteFromLines(@PathVariable String id) {
        productLineService.delete(id);
    }
}
