package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.OwnershipService;
import de.toomuchcoffee.view.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/collections")
@CrossOrigin
public class CollectionController {
    @Autowired
    private OwnershipService ownershipService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public CollectionDto getCollection(@PathVariable String username, @RequestParam(required = false) String verbatim) {
        if (verbatim != null) {
            return ownershipService.getCollectionByVerbatimLike(username, verbatim);
        }
        return ownershipService.getCollection(username);
    }

    @RequestMapping(value = "{username}/line/{productLine}", method = RequestMethod.GET)
    public CollectionDto getCollectionByProductLine(@PathVariable String username, @PathVariable String productLine) {
        return ownershipService.getCollectionByProductLine(username, productLine);
    }

}
