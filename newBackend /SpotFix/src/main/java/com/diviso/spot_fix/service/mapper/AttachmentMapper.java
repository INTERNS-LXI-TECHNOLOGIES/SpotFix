package com.diviso.spot_fix.service.mapper;

import com.diviso.spot_fix.domain.Attachment;
import com.diviso.spot_fix.domain.Ticket;
import com.diviso.spot_fix.domain.User;
import com.diviso.spot_fix.service.dto.AttachmentDTO;
import com.diviso.spot_fix.service.dto.TicketDTO;
import com.diviso.spot_fix.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attachment} and its DTO {@link AttachmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttachmentMapper extends EntityMapper<AttachmentDTO, Attachment> {
    @Mapping(target = "ticket", source = "ticket", qualifiedByName = "ticketId")
    @Mapping(target = "uploadedBy", source = "uploadedBy", qualifiedByName = "userLogin")
    AttachmentDTO toDto(Attachment s);

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
