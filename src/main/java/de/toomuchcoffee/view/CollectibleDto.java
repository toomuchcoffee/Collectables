package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Tag;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by gerald on 06/04/16.
 */
public class CollectibleDto {
    private String verbatim;

    private String productLine;

    private String tags;

    public String getVerbatim() {
        return verbatim;
    }

    public void setVerbatim(String verbatim) {
        this.verbatim = verbatim;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public static CollectibleDto toDto(Collectible collectible) {
        CollectibleDto dto = new CollectibleDto();

        dto.setVerbatim(collectible.getVerbatim());

        Optional.ofNullable(collectible.getProductLine())
                .ifPresent(p -> dto.setProductLine(p.getAbbreviation()));

        dto.setTags(collectible.getTags().size()>0
                ? "#" + String.join(" #", collectible.getTags().stream().map(Tag::getName).collect(Collectors.toList()))
                : null
        );

        return dto;
    }

}
