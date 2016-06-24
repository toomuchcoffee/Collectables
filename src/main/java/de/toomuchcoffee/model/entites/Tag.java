package de.toomuchcoffee.model.entites;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
public class Tag extends AbstractEntity {
    @Id
    private String name;

    @ManyToMany(mappedBy="tags", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Collectible> collectibles;

    public Tag() {}

    public Tag(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Collectible> getCollectibles() {
        return collectibles;
    }

    public void setCollectibles(Set<Collectible> collectibles) {
        this.collectibles = collectibles;
    }
}
