package de.toomuchcoffee.model.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
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
}
