package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.services.ProductLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/lines")
@CrossOrigin
public class ProductLineController {

    @Autowired
    private ProductLineService productLineService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ProductLine> allProductLines() {
        return productLineService.findAll();
    }

}
