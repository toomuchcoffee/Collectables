package de.toomuchcoffee.model.entites;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProductLine {
    @Id
    private String code;

    private String description;

    public ProductLine() {
    }

    public ProductLine(String code) {
        this.code = code;
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
}
