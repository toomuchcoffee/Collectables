package de.toomuchcoffee.view;

public class ProductLineDto {
    private String code;
    private String description;
    private Integer collectiblesCount;

    public ProductLineDto(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
