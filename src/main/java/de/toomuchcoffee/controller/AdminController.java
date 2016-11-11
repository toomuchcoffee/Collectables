package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.services.*;
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

    @Autowired
    private TumblrService tumblrService;


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

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> findUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public void createUser(@RequestBody NewUserDto newUser) {
        userService.create(newUser);
    }

    @RequestMapping(value = "/tumblr/refresh", method = RequestMethod.GET)
    public void readTumblrPosts() {
        tumblrService.readPosts();
    }

    @RequestMapping(value = "/tumblr/timestamp", method = RequestMethod.GET)
    public Long readTumblrTimestamp() {
        return tumblrService.getTimestamp();
    }
}
