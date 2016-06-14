package de.toomuchcoffee.model.entites;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ownership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "collector_id")
    private Collector collector;

    @ManyToOne
    @JoinColumn(name = "collectible_id")
    private Collectible collectible;

    @Column(precision=8, scale=2)
    private BigDecimal price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Collector getCollector() {
        return collector;
    }

    public void setCollector(Collector collector) {
        this.collector = collector;
    }

    public Collectible getCollectible() {
        return collectible;
    }

    public void setCollectible(Collectible collectible) {
        this.collectible = collectible;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
