package de.toomuchcoffee.view;

import java.math.BigDecimal;

public class OwnershipDto {
    private Long id;

    private String username;

    private BigDecimal price;

    private Boolean moc;

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

    public Boolean isMoc() {
        return moc;
    }

    public void setMoc(Boolean moc) {
        this.moc = moc;
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

}
