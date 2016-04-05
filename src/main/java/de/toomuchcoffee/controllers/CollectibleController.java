package de.toomuchcoffee.controllers;

import de.toomuchcoffee.entites.Collectible;
import de.toomuchcoffee.repositories.CollectibleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gerald.sander on 04/04/16.
 */
@Controller
@RequestMapping("/")
public class CollectibleController {

    @Autowired
    private CollectibleRepository collectibleRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String allCollectibles(Model model) {
        List<Collectible> collectibles = collectibleRepository.findAll();
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }

    @RequestMapping(value = "/{year}", method = RequestMethod.GET)
    public String collectiblesByYear(@PathVariable int year, Model model) {
        List<Collectible> collectibles = collectibleRepository.findByYear(year);
        model.addAttribute("collectibles", collectibles);
        return "collectibles";
    }

    @RequestMapping(value = "/{year}", method = RequestMethod.POST)
    public String addToCollectibles(@PathVariable int year, Collectible collectible) {
        collectible.setYear(year);
        collectibleRepository.save(collectible);
        return "redirect:/{year}";
    }
}
