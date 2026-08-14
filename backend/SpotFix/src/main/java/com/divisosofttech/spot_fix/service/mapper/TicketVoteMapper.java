package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.TicketVote;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.dto.TicketVoteDTO;
import com.divisosofttech.spot_fix.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TicketVote} and its DTO {@link TicketVoteDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketVoteMapper extends EntityMapper<TicketVoteDTO, TicketVote> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    TicketVoteDTO toDto(TicketVote s);

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
