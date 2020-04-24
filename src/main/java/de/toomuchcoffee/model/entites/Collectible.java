package de.toomuchcoffee.model.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
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

}
