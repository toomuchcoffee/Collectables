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

    @Enumerated(EnumType.STRING)
    private ProductLine productLine;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name="tagging", joinColumns=@JoinColumn(name="collectible_id"), inverseJoinColumns=@JoinColumn(name="tag_id"))
    private Set<Tag> tags;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent_id")
    private Collectible partOf;

    @OneToMany(mappedBy="partOf", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Collectible> contains;

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

    public Collectible getPartOf() {
        return partOf;
    }

    public void setPartOf(Collectible partOf) {
        this.partOf = partOf;
    }

    public Set<Collectible> getContains() {
        return contains;
    }

    public void setContains(Set<Collectible> contains) {
        this.contains = contains;
    }
}
