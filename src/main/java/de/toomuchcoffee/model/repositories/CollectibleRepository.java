package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface CollectibleRepository extends JpaRepository<Collectible, Long>{
    List<Collectible> findByTags(Set<Tag> tags);
    List<Collectible> findByProductLine(ProductLine productLine);

    List<Collectible> findByProductLineAbbreviation(String productLineAbbreviation);
    List<Collectible> findByVerbatimIgnoreCaseContaining(String verbatim);
    List<Collectible> findByProductLineAbbreviationAndVerbatimIgnoreCaseContaining(String productLineAbbreviation, String verbatim);
}
