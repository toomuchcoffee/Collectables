package de.toomuchcoffee.view;

import java.math.BigDecimal;
import java.util.List;

public class CollectionDto {
    private List<OwnershipDto> ownerships;

    private Integer size;

    private BigDecimal value;

    public List<OwnershipDto> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(List<OwnershipDto> ownerships) {
        this.ownerships = ownerships;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
