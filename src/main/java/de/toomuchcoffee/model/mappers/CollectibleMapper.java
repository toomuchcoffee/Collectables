package de.toomuchcoffee.model.mappers;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.services.ProductLineService;
import de.toomuchcoffee.model.services.TagService;
import de.toomuchcoffee.view.CollectibleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Component
@RequiredArgsConstructor
public class CollectibleMapper {

    private final CollectibleRepository collectibleRepository;

    private final TagService tagService;

    private final ProductLineService productLineService;

    public Collectible mergeWithEntity(CollectibleDto collectibleDto, Collectible collectible) {
        collectible.setVerbatim(collectibleDto.getVerbatim());

        if (collectibleDto.getPlacementNo() != null) {
            collectible.setPlacementNo(collectibleDto.getPlacementNo().toUpperCase());
        } else {
            collectible.setPlacementNo(null);
        }

        ProductLine productLine = ProductLine.valueOf(collectibleDto.getProductLine());
        collectible.setProductLine(productLine);

        if (collectibleDto.getTags() != null) {
            collectible.setTags(getTagsFromString(collectibleDto.getTags()));
        } else {
            collectible.setTags(Sets.newHashSet());
        }

        Collectible parent = getParent(productLine, collectibleDto.getPartOf());
        checkCircularRelation(collectible, parent);
        collectible.setPartOf(parent);

        return collectible;
    }

    private Collectible getParent(ProductLine line, String verbatim) {
        Collectible parent = null;

        if (verbatim != null) {
            List<Collectible> matches = collectibleRepository.findByProductLineAndVerbatimIgnoreCaseContaining(line, verbatim);
            if (matches.size() > 1) {
                throw new RuntimeException("Too many matches for parents found: " + matches);
            } else if (matches.size() == 1) {
                parent = matches.get(0);
            } else {
                parent = new Collectible();
                parent.setVerbatim(verbatim);
                parent.setProductLine(line);
            }
        }

        // TODO set 'contains' property

        return parent;
    }

    private void checkCircularRelation(Collectible collectible, Collectible parent) {
        if (parent != null) {
            Collectible aParent = parent;
            while (aParent != null) {
                if (collectible.equals(aParent))
                    throw new RuntimeException("Circular relation for 'partOf' property is not allowed");
                aParent = parent.getPartOf();
            }
        }
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

        dto.setProductLine(collectible.getProductLine().name());

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
