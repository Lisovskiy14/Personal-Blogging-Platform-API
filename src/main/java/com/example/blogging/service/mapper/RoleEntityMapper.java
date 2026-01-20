package com.example.blogging.service.mapper;

import com.example.blogging.domain.Role;
import com.example.blogging.repository.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {
    Role toRole(RoleEntity roleEntity);
    RoleEntity toRoleEntity(Role role);
}
