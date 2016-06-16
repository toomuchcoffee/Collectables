package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.services.CollectibleService;
import de.toomuchcoffee.model.services.ProductLineService;
import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.model.services.UserService;
import de.toomuchcoffee.view.CollectibleDto;
import de.toomuchcoffee.view.NewUserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/collectibles/{id}", method = RequestMethod.PUT)
    public void modifyCollectible(@PathVariable Long id, @RequestBody CollectibleDto collectible) {
        collectibleService.update(id, collectible);
    }

    @RequestMapping(value = "/collectibles/{id}", method = RequestMethod.DELETE)
    public void deleteCollectible(@PathVariable Long id) {
        collectibleService.delete(id);
    }

    @RequestMapping(value = "/tags/{id}", method = RequestMethod.DELETE)
    public void deleteTag(@PathVariable String id) {
        tagService.delete(id);
    }

    @RequestMapping(value = "/lines/{id}", method = RequestMethod.DELETE)
    public void deleteProductLine(@PathVariable String id) {
        productLineService.delete(id);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> findAll() {
        return userService.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void create(@RequestBody NewUserDto newUser) {
        userService.create(newUser);
    }
}
