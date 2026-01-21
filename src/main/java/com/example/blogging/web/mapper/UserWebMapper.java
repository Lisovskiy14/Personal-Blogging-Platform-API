package com.example.blogging.web.mapper;

import com.example.blogging.domain.User;
import com.example.blogging.dto.user.UserResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleWebMapper.class)
public interface UserWebMapper {
    UserResponseDto toResponseDto(User user);
}
