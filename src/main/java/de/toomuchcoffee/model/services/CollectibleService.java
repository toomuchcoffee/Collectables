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

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.Optional.ofNullable;
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
        Collectible collectible = collectibleRepository.findOne(id);
        if (collectible == null) {
            throw new NoSuchElementException(String.format("Collectible with id %d does not exist", id));
        }
        if (!Objects.equals(id, collectibleDto.getId())) {
            throw new IllegalArgumentException(String.format("Id %d does not match with dto id %d", id, collectibleDto.getId()));
        }
        Collectible mergedCollectible = mergeWithEntity(collectibleDto, collectible);
        collectibleRepository.save(mergedCollectible);
    }

    private Collectible mapToEntity(CollectibleDto collectibleDto) {
        return mergeWithEntity(collectibleDto, new Collectible());
    }

    private Collectible mergeWithEntity(CollectibleDto collectibleDto, Collectible collectible) {
        collectible.setVerbatim(collectibleDto.getVerbatim());

        if (collectibleDto.getPlacementNo() != null) {
            collectible.setPlacementNo(collectibleDto.getPlacementNo().toUpperCase());
        } else {
            collectible.setPlacementNo(null);
        }

        collectible.setProductLine(getProductLineFromCode(collectibleDto.getProductLine()));

        if (collectibleDto.getTags() != null) {
            collectible.setTags(getTagsFromString(collectibleDto.getTags()));
        } else {
            collectible.setTags(Sets.newHashSet());
        }

        return collectible;
    }

    private ProductLine getProductLineFromCode(String code) {
        return ofNullable(productLineService.find(code))
                .orElse(new ProductLine(code.toUpperCase()));
    }

    private Set<Tag> getTagsFromString(String tagsString) {
        return Arrays.asList(tagsString.split("#")).stream()
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(t -> ofNullable(tagService
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
        if (!isNullOrEmpty(productLine) && !isNullOrEmpty(verbatim)) {
            collectibles = collectibleRepository.findByProductLineCodeIgnoreCaseContainingAndVerbatimIgnoreCaseContaining(productLine, verbatim);
        }
        else if (!isNullOrEmpty(productLine)) {
            collectibles = collectibleRepository.findByProductLineCodeIgnoreCaseContaining(productLine);
        }
        else if (!isNullOrEmpty(verbatim)) {
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
                .sorted(comparing(CollectibleDto::getId))
                .sorted(comparing(CollectibleDto::getSecondarySorting, nullsLast(Integer::compareTo)))
                .sorted(comparing(CollectibleDto::getPrimarySorting, nullsLast(String::compareTo)))
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
