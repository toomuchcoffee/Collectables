package de.toomuchcoffee.model.services;

import de.toomuchcoffee.model.entites.Collectible;
import de.toomuchcoffee.model.entites.Collector;
import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.model.repositories.CollectibleRepository;
import de.toomuchcoffee.model.repositories.OwnershipRepository;
import de.toomuchcoffee.model.repositories.UserRepository;
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

    public void add(OwnershipDto ownershipDto) {
        Ownership ownership = mapToEntity(ownershipDto);
        ownershipRepository.save(ownership);
    }

    public void delete(Long id) {
        ownershipRepository.delete(id);
    }

    public List<OwnershipDto> findByCollectorIdAndCollectibleId(String collectorId, Long collectibleId) {
        Collector collector = userRepository.findOne(collectorId);
        Collectible collectible = collectibleRepository.findOne(collectibleId);
        List<Ownership> ownerships = ownershipRepository.findByCollectorAndCollectible(collector, collectible);
        return ownerships.stream().map(OwnershipDto::toDto).collect(Collectors.toList());
    }

    private Ownership mapToEntity(OwnershipDto ownershipDto) {
        Ownership ownership = new Ownership();
        ownership.setId(ownershipDto.getId());
        ownership.setPrice(ownershipDto.getPrice());
        ownership.setCollectible(collectibleRepository.findOne(ownershipDto.getCollectibleId()));
        ownership.setCollector(userRepository.getOne(ownershipDto.getCollectorId()));
        return ownership;
    }
}
