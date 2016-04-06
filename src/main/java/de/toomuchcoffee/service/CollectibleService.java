package de.toomuchcoffee.service;

import com.google.common.collect.Sets;
import de.toomuchcoffee.dtos.CollectibleDto;
import de.toomuchcoffee.entites.Collectible;
import de.toomuchcoffee.entites.ProductLine;
import de.toomuchcoffee.entites.Tag;
import de.toomuchcoffee.repositories.CollectibleRepository;
import de.toomuchcoffee.repositories.ProductLineRepository;
import de.toomuchcoffee.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

        ProductLine productLine = productLineRepository.findOne(collectibleDto.getProductLine());
        collectible.setProductLine(productLine);

        Set<Tag> tags = Arrays.asList(collectibleDto.getTags().split("#")).stream()
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(t -> Optional.ofNullable(tagRepository.findOne(t)).orElse(new Tag(t)))
                .collect(Collectors.toSet());

        collectible.setTags(tags);

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

}
