package com.example.blogging.web.mapper;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleResponseDto;
import com.example.blogging.dto.role.ShortRoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PermissionWebMapper.class)
public interface RoleWebMapper {
    RoleResponseDto toResponseDto(Role role);
}
