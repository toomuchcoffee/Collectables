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
    public CollectionDto getCollection(@PathVariable String username) {
        return ownershipService.getCollection(username);
    }
}
