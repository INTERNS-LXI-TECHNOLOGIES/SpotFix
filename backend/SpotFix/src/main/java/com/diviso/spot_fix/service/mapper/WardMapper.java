package com.diviso.spot_fix.service.mapper;

import com.diviso.spot_fix.domain.Ward;
import com.diviso.spot_fix.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ward} and its DTO {@link WardDTO}.
 */
@Mapper(componentModel = "spring")
public interface WardMapper extends EntityMapper<WardDTO, Ward> {}
