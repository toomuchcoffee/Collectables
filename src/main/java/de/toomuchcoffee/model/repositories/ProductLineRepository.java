package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
}
