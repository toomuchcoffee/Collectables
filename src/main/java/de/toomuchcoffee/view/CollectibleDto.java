package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.ProductLine;

import java.util.Set;

public class CollectibleDto {
    private Long id;

    private String verbatim;

    private ProductLine productLine;

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

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
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

}
