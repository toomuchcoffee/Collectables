package de.toomuchcoffee.view;

import de.toomuchcoffee.model.entites.ProductLine;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class CollectionDto {
    private List<OwnershipDto> ownerships;

    private Integer size;

    private BigDecimal value;

    private Map<ProductLine, Long> ownedLines;

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

    public Map<ProductLine, Long> getOwnedLines() {
        return ownedLines;
    }

    public void setOwnedLines(Map<ProductLine, Long> ownedLines) {
        this.ownedLines = ownedLines;
    }
}
