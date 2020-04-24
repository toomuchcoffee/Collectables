package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.OwnershipService;
import de.toomuchcoffee.view.ModifyOwnershipDto;
import de.toomuchcoffee.view.NewOwnershipDto;
import de.toomuchcoffee.view.OwnershipDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/ownerships")
@CrossOrigin
@RequiredArgsConstructor
public class OwnershipController {

    private final OwnershipService ownershipService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void addOwnership(@RequestBody NewOwnershipDto ownership) {
        ownershipService.add(ownership);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public void modifyOwnership(@PathVariable Long id, @RequestBody ModifyOwnershipDto ownership) {
        ownershipService.modify(id, ownership);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteOwnership(@PathVariable Long id) {
        ownershipService.delete(id);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<OwnershipDto> find(@RequestParam String username, @RequestParam(name = "collectible_id", required = false) Long collectibleId) {
        return ownershipService.findByUsernameAndCollectibleId(username, collectibleId);
    }
}
