package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import de.toomuchcoffee.view.TagDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag find(String tagId) {
        return tagRepository.findById(tagId.toLowerCase()).orElse(null);
    }

    public List<TagDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(t -> new TagDto(t.getName(), t.getCollectibles().size()))
                .collect(toList());
    }

    @Transactional
    public void delete(String id) {
        tagRepository.deleteById(id);
    }
}
