package de.toomuchcoffee.controller;

import de.toomuchcoffee.model.services.ProductLineService;
import de.toomuchcoffee.view.ProductLineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/lines")
@CrossOrigin
@RequiredArgsConstructor
public class ProductLineController {

    private final ProductLineService productLineService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<ProductLineDto> allProductLines() {
        return productLineService.findAll();
    }

}
