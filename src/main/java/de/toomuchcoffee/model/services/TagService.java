package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import de.toomuchcoffee.view.TagDto;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TagService {

    public static final String REGEX_EXCLUDING_WHITESPACE = "[^a-zA-Z0-9]";
    public static final String REGEX_INCLUDING_WHITESPACE = "[^a-zA-Z0-9 ]";
    public static final String REGEX_PARENTHESES_WITH_GROUP = "\\((.*?)\\)";

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

    public Pair<Set<String>,Set<String>> getVerbatimTags(String verbatim) {
        Pattern pattern = Pattern.compile(REGEX_PARENTHESES_WITH_GROUP);
        Matcher matcher = pattern.matcher(verbatim);

        String verbatimMain = verbatim.replaceAll(REGEX_PARENTHESES_WITH_GROUP, "").trim().toLowerCase();
        Set<String> verbatimAdditions = Sets.newHashSet();

        while (matcher.find()) {
            verbatimAdditions.add(matcher.group(1).trim().toLowerCase());
        }

        String verbatimNoWhitespace = verbatimMain.replaceAll(REGEX_EXCLUDING_WHITESPACE, "").trim();
        String verbatimWithWhitespace = verbatimMain.replaceAll(REGEX_INCLUDING_WHITESPACE, "").trim();

        Set<String> leftTags = Sets.newHashSet();

        leftTags.add(verbatimNoWhitespace);
        leftTags.add(verbatimWithWhitespace);

        Set<String> rightTags = Sets.newHashSet();
        for (String verbatimAddition : verbatimAdditions) {
            rightTags.add(verbatimAddition.replaceAll(REGEX_EXCLUDING_WHITESPACE, "").trim());
            rightTags.add(verbatimAddition.replaceAll(REGEX_INCLUDING_WHITESPACE, "").trim());
        }

        return Pair.of(leftTags, rightTags);
    }
}
