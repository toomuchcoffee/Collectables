package de.toomuchcoffee.model.entites;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Collectible extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String verbatim;

    private String placementNo;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private ProductLine productLine;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
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

    public String getPlacementNo() {
        return placementNo;
    }

    public void setPlacementNo(String placementNo) {
        this.placementNo = placementNo;
    }
}
