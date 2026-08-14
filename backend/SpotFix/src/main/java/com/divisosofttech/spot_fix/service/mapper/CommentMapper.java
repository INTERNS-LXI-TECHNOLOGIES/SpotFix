package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.Comment;
import com.divisosofttech.spot_fix.domain.Ticket;
import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.service.dto.CommentDTO;
import com.divisosofttech.spot_fix.service.dto.TicketDTO;
import com.divisosofttech.spot_fix.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Comment} and its DTO {@link CommentDTO}.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper extends EntityMapper<CommentDTO, Comment> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    CommentDTO toDto(Comment s);

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
