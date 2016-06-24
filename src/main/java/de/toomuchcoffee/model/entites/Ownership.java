package de.toomuchcoffee.model.entites;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Ownership extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "collectible_id")
    private Collectible collectible;

    @Column(precision=8, scale=2)
    private BigDecimal price;

    private Boolean moc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Boolean isMoc() {
        return moc;
    }

    public void setMoc(Boolean moc) {
        this.moc = moc;
    }
}
