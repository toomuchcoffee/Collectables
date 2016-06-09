package de.toomuchcoffee.view;

public class ProductLineDto {
    private String abbreviation;
    private String description;
    private Integer collectiblesCount;

    public ProductLineDto(String abbreviation, String description) {
        this.abbreviation = abbreviation;
        this.description = description;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCollectiblesCount() {
        return collectiblesCount;
    }

    public void setCollectiblesCount(Integer collectiblesCount) {
        this.collectiblesCount = collectiblesCount;
    }
}
