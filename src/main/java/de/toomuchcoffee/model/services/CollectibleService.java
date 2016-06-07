package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.ProductLineRepository;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectibleService {
    @Autowired
    private CollectibleRepository collectibleRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private ProductLineRepository productLineRepository;

    @Transactional
    public void add(CollectibleDto collectibleDto) {
        Collectible collectible = mapToEntity(collectibleDto);
        collectibleRepository.save(collectible);
    }

    @Transactional
    public void update(Long id, CollectibleDto collectibleDto) {
        if (!collectibleRepository.exists(id)) {
            throw new NoSuchElementException(String.format("Collectible with id %d does not exist", id));
        }
        if (!Objects.equals(id, collectibleDto.getId())) {
            throw new IllegalArgumentException(String.format("Id %d does not match with dto id %d", id, collectibleDto.getId()));
        }
        Collectible collectible = mapToEntity(collectibleDto);
        collectibleRepository.save(collectible);
    }

    private Collectible mapToEntity(CollectibleDto collectibleDto) {
        Collectible collectible = new Collectible();
        collectible.setVerbatim(collectibleDto.getVerbatim());

        collectible.setProductLine(
                Optional.ofNullable(productLineRepository.findOne(collectibleDto.getProductLine().toLowerCase()))
                .orElse(new ProductLine(collectibleDto.getProductLine().toLowerCase())));

        Optional.ofNullable(collectibleDto.getTags())
                .ifPresent(tagsString -> collectible.setTags(tagService.getTagsFromString(tagsString)));

        Optional.ofNullable(collectibleDto.getId()).ifPresent(collectible::setId);

        return collectible;
    }

    @Transactional
    public void delete(Long id) {
        collectibleRepository.delete(id);
    }

    public List<CollectibleDto> find() {
        List<Collectible> collectibles = collectibleRepository.findAll();
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByTagName(String tagName) {
        Tag tag = tagService.find(tagName);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByProductLineName(String productLineName) {
        ProductLine productLine = productLineRepository.findByAbbreviationIgnoreCase(productLineName);
        List<Collectible> collectibles = collectibleRepository.findByProductLine(productLine);
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public byte[] getCollectibleThumbnail(Long collectibleId) {
        Collectible collectible = collectibleRepository.findOne(collectibleId);
        return imageService.getCollectibleThumbnail(collectible);
    }
}
