package de.toomuchcoffee.entites;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by gerald.sander on 04/04/16.
 */
@Entity
public class Collectible {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String verbatim;

    @ManyToOne
    private ProductLine productLine;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="tagging", joinColumns=@JoinColumn(name="collectible_id"), inverseJoinColumns=@JoinColumn(name="tag_id"))
    private Set<Tag> tags;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVerbatim() {
        return verbatim;
    }

    public void setVerbatim(String verbatim) {
        this.verbatim = verbatim;
    }

    public ProductLine getProductLine() {
        return productLine;
    }

    public void setProductLine(ProductLine productLine) {
        this.productLine = productLine;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

}
