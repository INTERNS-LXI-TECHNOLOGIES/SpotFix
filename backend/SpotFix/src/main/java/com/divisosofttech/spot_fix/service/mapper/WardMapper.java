package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ward} and its DTO {@link WardDTO}.
 */
@Mapper(componentModel = "spring")
public interface WardMapper extends EntityMapper<WardDTO, Ward> {}
