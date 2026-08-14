package com.divisosofttech.spot_fix.service.mapper;

import com.divisosofttech.spot_fix.domain.User;
import com.divisosofttech.spot_fix.domain.UserProfile;
import com.divisosofttech.spot_fix.service.dto.UserDTO;
import com.divisosofttech.spot_fix.service.dto.UserProfileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserProfile} and its DTO {@link UserProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserProfileMapper extends EntityMapper<UserProfileDTO, UserProfile> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    UserProfileDTO toDto(UserProfile s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
