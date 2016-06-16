package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.OwnershipService;
import de.toomuchcoffee.view.CollectionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/collectors")
@CrossOrigin
public class CollectorController {
    @Autowired
    private OwnershipService ownershipService;

    @RequestMapping(value = "{collectorId}/collection", method = RequestMethod.GET)
    public CollectionDto getCollection(@PathVariable String collectorId) {
        return ownershipService.getCollection(collectorId);
    }
}
