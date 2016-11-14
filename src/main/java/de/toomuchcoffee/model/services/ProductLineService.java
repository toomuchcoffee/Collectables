package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.view.ProductLineDto;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
public class ProductLineService {

    public List<ProductLineDto> findAll() {
        return newArrayList(ProductLine.values()).stream()
                .map(p -> new ProductLineDto(p.name(), p.verbatim, p.startYear, p.endYear))
                .collect(toList());
    }

}
