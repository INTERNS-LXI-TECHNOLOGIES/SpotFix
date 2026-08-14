package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.TicketStatusHistory;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.dto.TicketStatusHistoryDTO;
import com.divisosofttech.spot_fix.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketStatusHistory} and its DTO {@link TicketStatusHistoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketStatusHistoryMapper extends EntityMapper<TicketStatusHistoryDTO, TicketStatusHistory> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    @Mapping(target = "changedBy", source = "changedBy", qualifiedByName = "userLogin")
    TicketStatusHistoryDTO toDto(TicketStatusHistory s);

    @Named("ticketId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TicketDTO toDtoTicketId(Ticket ticket);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
