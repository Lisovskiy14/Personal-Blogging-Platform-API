package com.example.blogging.service.mapper;

import com.example.blogging.domain.Permission;
import com.example.blogging.repository.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionEntityMapper {
    Permission toPermission(PermissionEntity permissionEntity);
}
