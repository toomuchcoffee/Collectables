package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.services.ProductLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/lines")
@CrossOrigin
public class ProductLineController {

    @Autowired
    private ProductLineService productLineService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<String> allProductLines() {
        List<ProductLine> productLines = productLineService.findAll();
        return productLines.stream()
                .map(p -> Optional.ofNullable(p.getDescription()).orElse(p.getAbbreviation()))
                .collect(Collectors.toList());
    }

}
