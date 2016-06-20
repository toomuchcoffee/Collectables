package de.toomuchcoffee.model.services;

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
        List<Ownership> ownerships;
        User user = userRepository.findOne(username);
        if (collectibleId != null) {
            Collectible collectible = collectibleRepository.findOne(collectibleId);
            ownerships = ownershipRepository.findByUserAndCollectible(user, collectible);
        } else {
            ownerships = ownershipRepository.findByUser(user);
        }
        return ownerships.stream().map(OwnershipDto::toDto).collect(toList());
    }

    public CollectionDto getCollection(String username) {
        List<OwnershipDto> ownerships = findByUsernameAndCollectibleId(username, null);

        CollectionDto collection = new CollectionDto();

        collection.setOwnerships(ownerships);
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
