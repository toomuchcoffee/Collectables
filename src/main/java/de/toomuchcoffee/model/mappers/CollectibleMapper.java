package de.toomuchcoffee.model.mappers;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.services.ProductLineService;
import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.view.CollectibleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
public class CollectibleMapper {

    @Autowired
    private CollectibleRepository collectibleRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private ProductLineService productLineService;

    public Collectible mergeWithEntity(CollectibleDto collectibleDto, Collectible collectible) {
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

        Collectible parent = null;
        if (collectibleDto.getPartOf() != null) {
            List<Collectible> matches = collectibleRepository.findByVerbatimIgnoreCaseContaining(collectibleDto.getPartOf());
            if (matches.size() > 1) {
                throw new RuntimeException("Too many matches for parents found: " + matches);
            } else if (matches.size() == 1) {
                parent = matches.get(0);
            } else {
                parent = new Collectible();
                parent.setVerbatim(collectibleDto.getPartOf());
                parent.setProductLine(collectible.getProductLine());
            }
        }
        collectible.setPartOf(parent);

        // TODO set 'contains' property

        return collectible;
    }

    private ProductLine getProductLineFromCode(String code) {
        return ofNullable(productLineService.find(code))
                .orElse(new ProductLine(code.toUpperCase()));
    }

    private Set<Tag> getTagsFromString(String tagsString) {
        return Arrays.stream(tagsString.split("#"))
                .map(String::trim)
                .filter(s -> s.length() > 0)
                .map(t -> ofNullable(tagService
                        .find(t.toLowerCase()))
                        .orElse(new Tag(t.toLowerCase())))
                .collect(toSet());
    }

    public CollectibleDto toDto(Collectible collectible) {
        CollectibleDto dto = new CollectibleDto();

        dto.setId(collectible.getId());

        dto.setVerbatim(collectible.getVerbatim());

        dto.setPlacementNo(collectible.getPlacementNo());

        ofNullable(collectible.getProductLine())
                .ifPresent(p -> dto.setProductLine(p.getCode()));

        dto.setTags(
                (collectible.getTags().size()>0 ? "#" : "")
                        + String.join(" #", collectible.getTags().stream()
                        .map(Tag::getName)
                        .collect(toList()))
        );

        ofNullable(collectible.getPartOf())
                .ifPresent(p -> dto.setPartOf(p.getVerbatim()));

        dto.setContains(collectible.getContains().stream()
                .map(this::toDto)
                .collect(Collectors.toSet())
        );

        return dto;
    }
}