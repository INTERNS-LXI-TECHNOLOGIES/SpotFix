package com.diviso.spot_fix.service.mapper;

import com.diviso.spot_fix.domain.Location;
import com.diviso.spot_fix.domain.Ward;
import com.diviso.spot_fix.service.dto.LocationDTO;
import com.diviso.spot_fix.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardId")
    LocationDTO toDto(Location s);

    @Named("wardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WardDTO toDtoWardId(Ward ward);
}
