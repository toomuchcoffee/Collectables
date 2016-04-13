package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gerald.sander on 06/04/16.
 */
public interface TagRepository extends JpaRepository<Tag, String> {
    Tag findByNameIgnoreCase(String tagName);
}
