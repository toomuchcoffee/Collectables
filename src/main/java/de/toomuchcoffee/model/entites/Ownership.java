package de.toomuchcoffee.model.entites;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
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

    private boolean moc;

}
