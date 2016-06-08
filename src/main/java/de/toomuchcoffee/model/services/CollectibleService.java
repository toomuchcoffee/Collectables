package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CollectibleService {
    @Autowired
    private CollectibleRepository collectibleRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ProductLineService productLineService;

    @Autowired
    private ImageService imageService;

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

        collectible.setProductLine(getProductLineFromAbbreviation(collectibleDto.getProductLine()));

        Optional.ofNullable(collectibleDto.getTags())
                .ifPresent(tagsString -> collectible.setTags(getTagsFromString(tagsString)));

        Optional.ofNullable(collectibleDto.getId()).ifPresent(collectible::setId);

        return collectible;
    }

    private ProductLine getProductLineFromAbbreviation(String abbreviation) {
        return Optional.ofNullable(productLineService.find(abbreviation))
                .orElse(new ProductLine(abbreviation.toLowerCase()));
    }

    private Set<Tag> getTagsFromString(String tagsString) {
        return Arrays.asList(tagsString.split("#")).stream()
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(t -> Optional.ofNullable(tagService
                        .find(t.toLowerCase()))
                        .orElse(new Tag(t.toLowerCase())))
                .collect(Collectors.toSet());
    }

    @Transactional
    public void delete(Long id) {
        collectibleRepository.delete(id);
    }

    public List<CollectibleDto> find() {
        List<Collectible> collectibles = collectibleRepository.findAll();
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByQuery(String query) {
        List<Collectible> collectibles = collectibleRepository.findByVerbatimIgnoreCaseContaining(query);
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByTag(String tagId) {
        Tag tag = tagService.find(tagId);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public List<CollectibleDto> findByProductLine(String abbreviation) {
        ProductLine productLine = productLineService.find(abbreviation);
        List<Collectible> collectibles = collectibleRepository.findByProductLine(productLine);
        return collectibles.stream().map(CollectibleDto::toDto).collect(Collectors.toList());
    }

    public byte[] getCollectibleThumbnail(Long collectibleId) {
        Collectible collectible = collectibleRepository.findOne(collectibleId);
        return imageService.getCollectibleThumbnail(collectible);
    }
}
