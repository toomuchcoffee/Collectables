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

    private String productNo;

    private String productLine;

    private int year;

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

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public String getTagsString() {
        return getTags().size() == 0
                ? null
                : "#"+String.join(" #", getTags().stream()
                .map(Tag::getName)
                .collect(Collectors.toList()));
    }

    public void setTagsString(String tagsString) {
        List<String> strings = Arrays.asList(tagsString.split("#"));
        Set<Tag> tags = strings.stream()
                .map(String::trim)
                .filter(s -> s.length()>0)
                .map(Tag::new)
                .collect(Collectors.toSet());
        setTags(tags);
    }

}
