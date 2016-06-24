package de.toomuchcoffee.model.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class ProductLine extends AbstractEntity {
    @Id
    private String code;

    private String description;

    @OneToMany(mappedBy="productLine", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Collectible> collectibles;

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

    public List<Collectible> getCollectibles() {
        return collectibles;
    }

    public void setCollectibles(List<Collectible> collectibles) {
        this.collectibles = collectibles;
    }
}
