package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.Ownership;

import java.math.BigDecimal;

public class OwnershipDto {
    private Long id;

    private String collectorId;

    private Long collectibleId;

    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static OwnershipDto toDto(Ownership ownership) {
        OwnershipDto dto = new OwnershipDto();
        dto.setId(ownership.getId());
        dto.setCollectibleId(ownership.getCollectible().getId());
        dto.setCollectorId(ownership.getCollector().getUsername());
        dto.setPrice(ownership.getPrice());
        return dto;
    }
}
