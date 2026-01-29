package com.example.blogging.service.mapper;

import com.example.blogging.domain.Role;
import com.example.blogging.repository.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PermissionEntityMapper.class)
public interface RoleEntityMapper {
    @Mapping(target = "parent.parent", ignore = true)
    @Mapping(target = "parent.children", ignore = true)
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
}
