package com.example.blogging.web.mapper;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleWebMapper {
    RoleResponseDto toResponseDto(Role role);
}
