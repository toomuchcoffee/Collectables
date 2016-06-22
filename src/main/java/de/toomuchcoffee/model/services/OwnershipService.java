package de.toomuchcoffee.model.services;

import com.google.common.base.Strings;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.OwnershipRepository;
import de.toomuchcoffee.model.repositories.UserRepository;
import de.toomuchcoffee.view.CollectionDto;
import de.toomuchcoffee.view.ModifyOwnershipDto;
import de.toomuchcoffee.view.NewOwnershipDto;
import de.toomuchcoffee.view.OwnershipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OwnershipService {
    @Autowired
    private OwnershipRepository ownershipRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CollectibleRepository collectibleRepository;

    public void add(NewOwnershipDto ownershipDto) {
        Ownership ownership = mapToEntity(ownershipDto);
        ownershipRepository.save(ownership);
    }

    public void modify(Long id, ModifyOwnershipDto modifyOwnership) {
        Ownership ownership = ownershipRepository.findOne(id);
        ownership.setPrice(modifyOwnership.getPrice());
        ownership.setMoc(modifyOwnership.isMoc());
        ownershipRepository.save(ownership);
    }

    public void delete(Long id) {
        ownershipRepository.delete(id);
    }

    public List<OwnershipDto> findByUsernameAndCollectibleId(String username, Long collectibleId) {
        User user = userRepository.findOne(username);
        Collectible collectible = collectibleRepository.findOne(collectibleId);
        List<Ownership> ownerships = ownershipRepository.findByUserAndCollectible(user, collectible);
        return ownerships.stream().map(OwnershipDto::toDto).collect(toList());
    }

    public CollectionDto getCollection(String username, String productLine, String verbatim) {
        List<Ownership> ownerships;
        if (!Strings.isNullOrEmpty(productLine) && !Strings.isNullOrEmpty(verbatim)) {
            ownerships = ownershipRepository
                    .findByUserUsernameAndCollectibleProductLineAbbreviationIgnoreCaseContainingAndCollectibleVerbatimIgnoreCaseContaining(
                    username, productLine, verbatim);
        } else if (!Strings.isNullOrEmpty(productLine)) {
            ownerships = ownershipRepository
                    .findByUserUsernameAndCollectibleProductLineAbbreviationIgnoreCaseContaining(username, productLine);
        } else if (!Strings.isNullOrEmpty(verbatim)) {
            ownerships = ownershipRepository
                    .findByUserUsernameAndCollectibleVerbatimIgnoreCaseContaining(username, verbatim);
        } else {
            ownerships = ownershipRepository.findByUserUsername(username);
        }
        return getCollection(ownerships);
    }

    private CollectionDto getCollection(List<Ownership> ownerships) {
        CollectionDto collection = new CollectionDto();
        List<OwnershipDto> ownershipDtos = ownerships.stream()
                .map(OwnershipDto::toDto)
                .collect(toList());
        collection.setOwnerships(ownershipDtos);
        collection.setSize(ownerships.size());

        BigDecimal value = ownerships.stream()
                .map(o -> o.getPrice()==null ? BigDecimal.ZERO : o.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        collection.setValue(value);
        return collection;
    }

    private Ownership mapToEntity(NewOwnershipDto ownershipDto) {
        Ownership ownership = new Ownership();
        ownership.setCollectible(collectibleRepository.findOne(ownershipDto.getCollectibleId()));
        ownership.setUser(userRepository.getOne(ownershipDto.getUsername()));
        return ownership;
    }
}
