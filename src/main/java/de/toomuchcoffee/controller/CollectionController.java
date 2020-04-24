package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.OwnershipService;
import de.toomuchcoffee.view.CollectionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/collections")
@CrossOrigin
@RequiredArgsConstructor
public class CollectionController {
    private final OwnershipService ownershipService;

    @RequestMapping(value = "{username}", method = RequestMethod.GET)
    public CollectionDto getCollection(
            @PathVariable String username,
            @RequestParam(required = false) String line,
            @RequestParam(required = false) String verbatim) {
        return ownershipService.getCollection(username, line, verbatim);
    }

}
