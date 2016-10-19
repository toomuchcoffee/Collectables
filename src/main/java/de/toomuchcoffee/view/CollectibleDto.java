package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Tag;

import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

public class CollectibleDto {
    private Long id;

    private String verbatim;

    private String productLine;

    private String tags;

    private String placementNo;

    private String partOf;

    private Set<CollectibleDto> contains;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPlacementNo() {
        return placementNo;
    }

    public void setPlacementNo(String placementNo) {
        this.placementNo = placementNo;
    }

    public String getPartOf() {
        return partOf;
    }

    public void setPartOf(String partOf) {
        this.partOf = partOf;
    }

    public Set<CollectibleDto> getContains() {
        return contains;
    }

    public void setContains(Set<CollectibleDto> contains) {
        this.contains = contains;
    }

    public String getPrimarySorting() {
        if (placementNo != null) {
            return placementNo.replaceAll("[0-9]", "").trim();
        }
        return null;
    }

    public Integer getSecondarySorting() {
        if (placementNo != null) {
            try {
                return Integer.valueOf(placementNo.replaceAll("[^0-9]", ""));
            } catch (NumberFormatException e) {
            }
        }
        return null;
    }

    public static CollectibleDto toDto(Collectible collectible) {
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
                        .map(CollectibleDto::toDto)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}
