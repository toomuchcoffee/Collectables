package de.toomuchcoffee.model.services;

import com.google.common.collect.Lists;
import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.entites.ProductLine;
import de.toomuchcoffee.model.entites.User;
import de.toomuchcoffee.model.mappers.OwnershipMapper;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.OwnershipRepository;
import de.toomuchcoffee.model.repositories.UserRepository;
import de.toomuchcoffee.view.CollectionDto;
import de.toomuchcoffee.view.ModifyOwnershipDto;
import de.toomuchcoffee.view.NewOwnershipDto;
import de.toomuchcoffee.view.OwnershipDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Collections.reverseOrder;
import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
public class OwnershipService {
    private final OwnershipRepository ownershipRepository;

    private final UserRepository userRepository;

    private final CollectibleRepository collectibleRepository;

    private final OwnershipMapper ownershipMapper;

    @Transactional
    public void add(NewOwnershipDto ownershipDto) {
        collectibleRepository.findById(ownershipDto.getCollectibleId()).ifPresent(collectible -> {
            User user = userRepository.getOne(ownershipDto.getUsername());
            Ownership ownership = new Ownership();
            ownership.setCollectible(collectible);
            ownership.setUser(user);
            ownershipRepository.save(ownership);
            addChildren(collectible, user);
        });
    }

    private void addChildren(Collectible collectible, User user) {
        for (Collectible aCollectible : collectible.getContains()) {
            Ownership ownership = new Ownership();
            ownership.setCollectible(aCollectible);
            ownership.setUser(user);
            ownershipRepository.save(ownership);
            addChildren(aCollectible, user);
        }
    }

    @Transactional
    public void modify(Long id, ModifyOwnershipDto modifyOwnership) {
        ownershipRepository.findById(id).ifPresent(ownership -> {
            ownership.setPrice(modifyOwnership.getPrice());
            ownership.setMoc(modifyOwnership.isMoc());
            ownershipRepository.save(ownership);
        });
    }

    public void delete(Long id) {
        ownershipRepository.deleteById(id);
    }

    public List<OwnershipDto> findByUsernameAndCollectibleId(String username, Long collectibleId) {
        Optional<User> user = userRepository.findById(username);
        Optional<Collectible> collectible = collectibleRepository.findById(collectibleId);
        if (user.isPresent() && collectible.isPresent()) {
            return ownershipRepository.findByUserAndCollectible(user.get(), collectible.get()).stream()
                    .map(ownershipMapper::toDto).collect(toList());
        }
        return Lists.newArrayList();
    }

    public CollectionDto getCollection(String username, String line, String verbatim) {
        List<Ownership> ownerships;
        if (!isNullOrEmpty(line) && !isNullOrEmpty(verbatim)) {
            ownerships = ownershipRepository
                    .findByUserUsernameAndCollectibleProductLineAndCollectibleVerbatimIgnoreCaseContaining(
                            username, ProductLine.valueOf(line), verbatim);
        } else if (!isNullOrEmpty(line)) {
            ownerships = ownershipRepository
                    .findByUserUsernameAndCollectibleProductLine(username, ProductLine.valueOf(line));
        } else if (!isNullOrEmpty(verbatim)) {
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
                .map(ownershipMapper::toDto)
                .collect(toList());
        collection.setOwnerships(ownershipDtos);
        collection.setSize(ownerships.size());

        Map<ProductLine, Long> ownedLines = ownerships.stream()
                .map(Ownership::getCollectible)
                .collect(groupingBy(Collectible::getProductLine, counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue(reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        collection.setOwnedLines(ownedLines);

        BigDecimal value = ownerships.stream()
                .map(o -> o.getPrice() == null ? BigDecimal.ZERO : o.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        collection.setValue(value);
        return collection;
    }

}
