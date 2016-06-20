package de.toomuchcoffee.view;

import java.math.BigDecimal;

public class ModifyOwnershipDto {

    private BigDecimal price;

    private Boolean moc;

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
}
