package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.domain.Location;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.Ward;
import com.divisosofttech.spot_fix.service.dto.DepartmentDTO;
import com.divisosofttech.spot_fix.service.dto.LocationDTO;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.dto.UserDTO;
import com.divisosofttech.spot_fix.service.dto.WardDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {
    @Mapping(target = "reportedBy", source = "reportedBy", qualifiedByName = "userLogin")
    @Mapping(target = "location", source = "location", qualifiedByName = "locationId")
    @Mapping(target = "ward", source = "ward", qualifiedByName = "wardId")
    @Mapping(target = "assignedDepartment", source = "assignedDepartment", qualifiedByName = "departmentId")
    TicketDTO toDto(Ticket s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("wardId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WardDTO toDtoWardId(Ward ward);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);
}
