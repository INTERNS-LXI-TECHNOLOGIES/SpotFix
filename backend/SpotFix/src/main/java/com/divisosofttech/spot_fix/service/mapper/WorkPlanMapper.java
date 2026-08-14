package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Department;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.WorkPlan;
import com.divisosofttech.spot_fix.service.dto.DepartmentDTO;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.dto.WorkPlanDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WorkPlan} and its DTO {@link WorkPlanDTO}.
 */
@Mapper(componentModel = "spring")
public interface WorkPlanMapper extends EntityMapper<WorkPlanDTO, WorkPlan> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentId")
    WorkPlanDTO toDto(WorkPlan s);

    @Named("ticketId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TicketDTO toDtoTicketId(Ticket ticket);

    @Named("departmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DepartmentDTO toDtoDepartmentId(Department department);
}
