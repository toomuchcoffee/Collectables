package de.toomuchcoffee.model.mappers;

import de.toomuchcoffee.model.entites.Ownership;
import de.toomuchcoffee.view.OwnershipDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OwnershipMapper {

    @Autowired
    private CollectibleMapper collectibleMapper;

    public OwnershipDto toDto(Ownership ownership) {
        OwnershipDto dto = new OwnershipDto();
        dto.setId(ownership.getId());
        dto.setCollectible(collectibleMapper.toDto(ownership.getCollectible()));
        dto.setUsername(ownership.getUser().getUsername());
        dto.setPrice(ownership.getPrice());
        dto.setMoc(ownership.isMoc());
        return dto;
    }
}
