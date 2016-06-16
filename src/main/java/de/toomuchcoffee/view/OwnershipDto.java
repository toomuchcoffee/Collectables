package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.Ownership;

import java.math.BigDecimal;

public class OwnershipDto {
    private Long id;

    private String username;

    private BigDecimal price;

    private CollectibleDto collectible;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public CollectibleDto getCollectible() {
        return collectible;
    }

    public void setCollectible(CollectibleDto collectible) {
        this.collectible = collectible;
    }

    public static OwnershipDto toDto(Ownership ownership) {
        OwnershipDto dto = new OwnershipDto();
        dto.setId(ownership.getId());
        dto.setCollectible(CollectibleDto.toDto(ownership.getCollectible()));
        dto.setUsername(ownership.getUser().getUsername());
        dto.setPrice(ownership.getPrice());
        return dto;
    }
}
