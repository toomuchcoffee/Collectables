package de.toomuchcoffee.view;

public class NewOwnershipDto {
    private String collectorId;

    private Long collectibleId;

    public String getCollectorId() {
        return collectorId;
    }

    public void setCollectorId(String collectorId) {
        this.collectorId = collectorId;
    }

    public Long getCollectibleId() {
        return collectibleId;
    }

    public void setCollectibleId(Long collectibleId) {
        this.collectibleId = collectibleId;
    }

}
