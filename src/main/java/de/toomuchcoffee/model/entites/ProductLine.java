package de.toomuchcoffee.model.entites;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by gerald on 06/04/16.
 */
@Entity
public class ProductLine {
    @Id
    private String abbreviation;

    private String description;

    public ProductLine() {
    }

    public ProductLine(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
