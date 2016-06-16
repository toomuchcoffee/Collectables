package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Collector;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.OwnershipRepository;
import de.toomuchcoffee.model.repositories.UserRepository;
import de.toomuchcoffee.view.NewOwnershipDto;
import de.toomuchcoffee.view.OwnershipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public void delete(Long id) {
        ownershipRepository.delete(id);
    }

    public List<OwnershipDto> findByCollectorIdAndCollectibleId(String collectorId, Long collectibleId) {
        List<Ownership> ownerships;
        Collector collector = userRepository.findOne(collectorId);
        if (collectibleId != null) {
            Collectible collectible = collectibleRepository.findOne(collectibleId);
            ownerships = ownershipRepository.findByCollectorAndCollectible(collector, collectible);
        } else {
            ownerships = ownershipRepository.findByCollector(collector);
        }
        return ownerships.stream().map(OwnershipDto::toDto).collect(Collectors.toList());
    }

    private Ownership mapToEntity(NewOwnershipDto ownershipDto) {
        Ownership ownership = new Ownership();
        ownership.setCollectible(collectibleRepository.findOne(ownershipDto.getCollectibleId()));
        ownership.setCollector(userRepository.getOne(ownershipDto.getCollectorId()));
        return ownership;
    }
}
