package com.example.blogging.service.mapper;

import com.example.blogging.domain.Role;
import com.example.blogging.repository.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    @Mapping(target = "parent.parent", ignore = true)
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
}
