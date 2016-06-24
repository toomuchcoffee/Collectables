package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.repositories.ProductLineRepository;
import de.toomuchcoffee.view.ProductLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ProductLineService {

    @Autowired
    private ProductLineRepository productLineRepository;

    public List<ProductLineDto> findAll() {
         List<ProductLine> productLines = productLineRepository.findAll();
        List<ProductLineDto> productLineDtos = productLines.stream()
                .map(p -> new ProductLineDto(p.getCode(), p.getDescription()))
                .collect(toList());
        productLineDtos.forEach(dto ->
                dto.setCollectiblesCount(productLineRepository.getCollectiblesCount(dto.getCode())));
        return productLineDtos;
    }

    public ProductLine find(String code) {
        return productLineRepository.findOne(code.toUpperCase());
    }

    public void delete(String id) {
        productLineRepository.delete(id);
    }
}
