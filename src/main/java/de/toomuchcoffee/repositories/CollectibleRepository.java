package de.toomuchcoffee.repositories;

import de.toomuchcoffee.entites.Collectible;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by gerald.sander on 04/04/16.
 */
public interface CollectibleRepository extends JpaRepository<Collectible, Long>{

    List<Collectible> findByYear(int year);

    List<Collectible> findByProductLine(String productLine);
}
