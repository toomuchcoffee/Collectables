package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag find(String tagName) {
        return tagRepository.findByNameIgnoreCase(tagName);
    }

    public Set<Tag> getTagsFromString(String tagsString) {
        return Arrays.asList(tagsString.split("#")).stream()
            .map(String::trim)
            .filter(s -> s.length() > 0)
                    .map(t -> Optional.ofNullable(tagRepository
                    .findOne(t.toLowerCase()))
                    .orElse(new Tag(t.toLowerCase())))
                    .collect(Collectors.toSet());
    }
}
