package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.OwnershipService;
import de.toomuchcoffee.view.NewOwnershipDto;
import de.toomuchcoffee.view.OwnershipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ownerships")
@CrossOrigin
public class OwnershipController {

    @Autowired
    private OwnershipService ownershipService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addOwnership(@RequestBody NewOwnershipDto ownership) {
        ownershipService.add(ownership);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteOwnership(@PathVariable Long id) {
        ownershipService.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OwnershipDto> find(@RequestParam String username, @RequestParam(name = "collectible_id", required = false) Long collectibleId) {
        return ownershipService.findByCollectorIdAndCollectibleId(username, collectibleId);
    }
}
