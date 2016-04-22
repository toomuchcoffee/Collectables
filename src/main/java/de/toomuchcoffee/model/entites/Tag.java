package de.toomuchcoffee.model.entites;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Tag {
    @Id
    private String name;

    @ManyToMany(mappedBy="tags", cascade = CascadeType.ALL)
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
