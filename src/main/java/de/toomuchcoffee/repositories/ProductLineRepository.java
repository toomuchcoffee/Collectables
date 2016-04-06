package de.toomuchcoffee.repositories;

import de.toomuchcoffee.entites.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gerald on 06/04/16.
 */
public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
}
