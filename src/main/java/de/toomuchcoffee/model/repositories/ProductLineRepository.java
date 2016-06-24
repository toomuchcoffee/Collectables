package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.ProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductLineRepository extends JpaRepository<ProductLine, String> {
    @Query(value = "SELECT count(c) FROM collectible c WHERE c.product_line_code = :code", nativeQuery = true)
    Integer getCollectiblesCount(@Param("code") String code);
}
