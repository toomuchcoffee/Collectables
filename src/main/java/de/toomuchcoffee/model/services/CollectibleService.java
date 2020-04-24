package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.mappers.CollectibleMapper;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.view.CollectibleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CollectibleService {
    private final CollectibleRepository collectibleRepository;

    private final TagService tagService;

    private final ImageService imageService;

    private final CollectibleMapper collectibleMapper;

    @Transactional
    public Long add(CollectibleDto collectibleDto) {
        Collectible collectible = mapToEntity(collectibleDto);
        collectibleRepository.save(collectible);
        return collectible.getId();
    }

    @Transactional
    public void update(Long id, CollectibleDto collectibleDto) {
        Collectible collectible = collectibleRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException(String.format("Collectible with id %d does not exist", id)));
        if (!Objects.equals(id, collectibleDto.getId())) {
            throw new IllegalArgumentException(String.format("Id %d does not match with dto id %d", id, collectibleDto.getId()));
        }
        Collectible mergedCollectible = collectibleMapper.mergeWithEntity(collectibleDto, collectible);
        collectibleRepository.save(mergedCollectible);
    }

    private Collectible mapToEntity(CollectibleDto collectibleDto) {
        return collectibleMapper.mergeWithEntity(collectibleDto, new Collectible());
    }


    @Transactional
    public void delete(Long id) {
        collectibleRepository.deleteById(id);
    }

    public CollectibleDto findOne(Long id) {
        return collectibleRepository.findById(id)
                .map(collectibleMapper::toDto)
                .orElseThrow(() -> new NoSuchElementException(String.format("Collectible with id %d does not exist", id)));
    }

    public List<CollectibleDto> find() {
        List<Collectible> collectibles = collectibleRepository.findAll();
        return sortedCollectibleDtos(collectibles);
    }

    public List<CollectibleDto> findByFilter(String verbatim, String line) {
        List<Collectible> collectibles;
        if (!isNullOrEmpty(line) && !isNullOrEmpty(verbatim)) {
            collectibles = collectibleRepository.findByProductLineAndVerbatimIgnoreCaseContaining(
                    ProductLine.valueOf(line), verbatim);
        } else if (!isNullOrEmpty(line)) {
            collectibles = collectibleRepository.findByProductLine(ProductLine.valueOf(line));
        } else if (!isNullOrEmpty(verbatim)) {
            collectibles = collectibleRepository.findByVerbatimIgnoreCaseContaining(verbatim);
        } else {
            collectibles = collectibleRepository.findAll();
        }
        return sortedCollectibleDtos(collectibles);
    }

    private List<CollectibleDto> sortedCollectibleDtos(List<Collectible> collectibles) {
        return collectibles.stream()
                .map(collectibleMapper::toDto)
                .sorted(comparing(CollectibleDto::getId))
                .sorted(comparing(CollectibleDto::getSecondarySorting, nullsLast(Integer::compareTo)))
                .sorted(comparing(CollectibleDto::getPrimarySorting, nullsLast(String::compareTo)))
                .collect(toList());
    }

    public List<CollectibleDto> findByTag(String tagId) {
        Tag tag = tagService.find(tagId);
        List<Collectible> collectibles = collectibleRepository.findByTags(Sets.newHashSet(tag));
        return collectibles.stream().map(collectibleMapper::toDto).collect(toList());
    }

    public byte[] getCollectibleThumbnail(Long collectibleId) {
        return collectibleRepository.findById(collectibleId)
                .map(imageService::getCollectibleThumbnail)
                .orElseThrow(() -> new NoSuchElementException(String.format("Collectible with id %d does not exist", collectibleId)));
    }
}
