package com.example.blogging.web.mapper;

import com.example.blogging.domain.Permission;
import com.example.blogging.dto.permission.PermissionResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionWebMapper {
    PermissionResponseDto toResponseDto(Permission permission);
}
