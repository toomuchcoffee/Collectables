package de.toomuchcoffee.repositories;

import de.toomuchcoffee.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gerald.sander on 06/04/16.
 */
public interface TagRepository extends JpaRepository<Tag, String> {

}
