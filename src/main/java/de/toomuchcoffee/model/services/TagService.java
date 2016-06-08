package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import de.toomuchcoffee.view.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag find(String tagId) {
        return tagRepository.findOne(tagId.toLowerCase());
    }

    public List<TagDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        List<TagDto> tagDtos = tags.stream()
                .map(t -> new TagDto(t.getName()))
                .collect(Collectors.toList());
        tagDtos.forEach(dto -> dto.setTaggingCount(tagRepository.getTaggingCount(dto.getName())));
        return tagDtos;
    }
}
