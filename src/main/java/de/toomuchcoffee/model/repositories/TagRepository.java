package de.toomuchcoffee.model.repositories;

import de.toomuchcoffee.model.entites.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends JpaRepository<Tag, String> {
    @Query(value = "SELECT count(t) FROM tagging t WHERE t.tag_id = :tag", nativeQuery = true)
    Integer getTaggingCount(@Param("tag") String tag);
}
