package de.toomuchcoffee.repositories;

import de.toomuchcoffee.entites.Collectible;
import de.toomuchcoffee.entites.ProductLine;
import de.toomuchcoffee.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

/**
 * Created by gerald.sander on 04/04/16.
 */
public interface CollectibleRepository extends JpaRepository<Collectible, Long>{

    List<Collectible> findByTags(Set<Tag> tags);

    List<Collectible> findByProductLine(ProductLine productLine);

}
