package de.toomuchcoffee.model.services;

import com.google.common.collect.Sets;
import de.toomuchcoffee.model.entites.Tag;
import de.toomuchcoffee.model.repositories.TagRepository;
import de.toomuchcoffee.view.TagDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag find(String tagId) {
        return tagRepository.findOne(tagId.toLowerCase());
    }

    public List<TagDto> findAll() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(t -> new TagDto(t.getName(), t.getCollectibles().size()))
                .collect(toList());
    }

    public Set<String> getVerbatimPermutations(String verbatim) {
        HashSet<String> permutations = Sets.newHashSet();

        String[] tokens = verbatim.replaceAll("[^a-zA-Z0-9]", " ").replaceAll("\\s+", " ").toLowerCase().split(" ");

        for (int x = 0; x < tokens.length; x++) {
            for (int y = 0; y < tokens.length - x; y++) {
                permutations.add(concatTokens(tokens, x, x+y, " "));
                permutations.add(concatTokens(tokens, x, x+y, ""));
            }
        }
        return permutations;
    }

    private String concatTokens(String[] tokens, int start, int end, String delimiter) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            if (sb.length() > 0) sb.append(delimiter);
            sb.append(tokens[i]);
        }
        return sb.toString();
    }

    @Transactional
    public void delete(String id) {
        tagRepository.delete(id);
    }
}
