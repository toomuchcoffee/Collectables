package de.toomuchcoffee.model.services;

import com.google.common.collect.Lists;
import de.toomuchcoffee.model.entites.ProductLine;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductLineService {

    public List<ProductLine> findAll() {
        return Lists.newArrayList(ProductLine.values());
    }

}
