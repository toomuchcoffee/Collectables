package de.toomuchcoffee.view;

public class TagDto {
    private String name;
    private Integer taggingCount;

    public TagDto(String name, Integer taggingCount) {
        this.name = name;
        this.taggingCount = taggingCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTaggingCount() {
        return taggingCount;
    }

    public void setTaggingCount(Integer taggingCount) {
        this.taggingCount = taggingCount;
    }
}
