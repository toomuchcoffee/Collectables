package de.toomuchcoffee.model.services;

import com.google.common.base.Strings;
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

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
    public Long add(CollectibleDto collectibleDto) {
        Collectible collectible = mapToEntity(collectibleDto);
        collectibleRepository.save(collectible);
        return collectible.getId();
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
        collectible.setPlacementNo(collectibleDto.getPlacementNo());

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
                .collect(toSet());
    }

    @Transactional
    public void delete(Long id) {
        collectibleRepository.delete(id);
    }

    public CollectibleDto findOne(Long id) {
        Collectible collectible = collectibleRepository.findOne(id);
        return CollectibleDto.toDto(collectible);
    }

    public List<CollectibleDto> find() {
        List<Collectible> collectibles = collectibleRepository.findAll();
        return sortedCollectibleDtos(collectibles);
    }

    public List<CollectibleDto> findByFilter(String verbatim, String productLine) {
        List<Collectible> collectibles;
        if (!Strings.isNullOrEmpty(productLine) && !Strings.isNullOrEmpty(verbatim)) {
            collectibles = collectibleRepository.findByProductLineAbbreviationIgnoreCaseContainingAndVerbatimIgnoreCaseContaining(productLine, verbatim);
        }
        else if (!Strings.isNullOrEmpty(productLine)) {
            collectibles = collectibleRepository.findByProductLineAbbreviationIgnoreCaseContaining(productLine);
        }
        else if (!Strings.isNullOrEmpty(verbatim)) {
            collectibles = collectibleRepository.findByVerbatimIgnoreCaseContaining(verbatim);
        }
        else {
            collectibles = collectibleRepository.findAll();
        }
        return sortedCollectibleDtos(collectibles);
    }

    private List<CollectibleDto> sortedCollectibleDtos(List<Collectible> collectibles) {
        return collectibles.stream()
                .map(CollectibleDto::toDto)
                .sorted(Comparator.comparing(CollectibleDto::getId))
                .sorted(Comparator.comparing(CollectibleDto::getSecondarySorting, Comparator.nullsLast(Integer::compareTo)))
                .sorted(Comparator.comparing(CollectibleDto::getPrimarySorting, Comparator.nullsLast(String::compareTo)))
                .collect(toList());
    }

    public List<CollectibleDto> findByTag(String tagId) {
        Tag tag = tagService.find(tagId);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        return collectibles.stream().map(CollectibleDto::toDto).collect(toList());
    }

    public byte[] getCollectibleThumbnail(Long collectibleId) {
        Collectible collectible = collectibleRepository.findOne(collectibleId);
        return imageService.getCollectibleThumbnail(collectible);
    }
}
