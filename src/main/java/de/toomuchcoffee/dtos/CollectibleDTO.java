package de.toomuchcoffee.dtos;

import de.toomuchcoffee.entites.Collectible;
import de.toomuchcoffee.entites.Tag;

import java.util.stream.Collectors;

/**
 * Created by gerald on 06/04/16.
 */
public class CollectibleDTO {
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

    public static CollectibleDTO toDto(Collectible collectible) {
        CollectibleDTO dto = new CollectibleDTO();

        dto.setVerbatim(collectible.getVerbatim());

        dto.setProductLine(collectible.getProductLine() != null
                ? collectible.getProductLine().getAbbreviation()
                : null);

        dto.setTags(collectible.getTags().size() > 0
                ? "#" + String.join(" #", collectible.getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()))
                : null);

        return dto;
    }

}
