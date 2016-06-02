package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.repositories.ProductLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lines")
@CrossOrigin
public class ProductLineController {

    @Autowired
    private ProductLineRepository productLineRepository;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<String> allProductLines() {
        List<ProductLine> tags = productLineRepository.findAll();
        return tags.stream().map(ProductLine::getAbbreviation).collect(Collectors.toList());
    }

}
