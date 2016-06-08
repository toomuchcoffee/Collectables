package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.repositories.ProductLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductLineService {

    @Autowired
    private ProductLineRepository productLineRepository;

    public List<ProductLine> findAll() {
        return productLineRepository.findAll();
    }

    public ProductLine find(String abbreviation) {
        return productLineRepository.findOne(abbreviation.toLowerCase());
    }

    public ProductLine getProductLineFromAbbreviation(String abbreviation) {
        return Optional.ofNullable(find(abbreviation))
                .orElse(new ProductLine(abbreviation.toLowerCase()));
    }
}
