package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.view.CollectibleDto;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.ProductLineRepository;
import de.toomuchcoffee.model.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by gerald on 06/04/16.
 */
@Service
public class CollectibleService {
    @Autowired
    private CollectibleRepository collectibleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductLineRepository productLineRepository;

    public void add(CollectibleDto collectibleDto) {
        Collectible collectible = new Collectible();
        collectible.setVerbatim(collectibleDto.getVerbatim());

        collectible.setProductLine(
                Optional.ofNullable(productLineRepository.findOne(collectibleDto.getProductLine()))
                .orElse(new ProductLine(collectibleDto.getProductLine())));

        collectible.setTags(Arrays.asList(collectibleDto.getTags().split("#")).stream()
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(t -> Optional.ofNullable(tagRepository.findOne(t)).orElse(new Tag(t)))
                .collect(Collectors.toSet()));

        collectibleRepository.save(collectible);
    }

    public List<CollectibleDto> find() {
        List<Collectible> collectibles = collectibleRepository.findAll();
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByTagName(String tagName) {
        Tag tag = tagRepository.findOne(tagName);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByProductLineName(String productLineName) {
        ProductLine productLine = productLineRepository.findOne(productLineName);
        List<Collectible> collectibles = collectibleRepository.findByProductLine(productLine);
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

}
