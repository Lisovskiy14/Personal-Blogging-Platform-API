package com.example.blogging.service;

import com.example.blogging.domain.Permission;
import com.example.blogging.repository.entity.PermissionEntity;

import java.util.Set;

public interface PermissionService {
    Set<Permission> getAllPermissions();
    Set<Permission> getPermissionsByRoleId(Long roleId);
    Permission getPermissionById(Long permissionId);
    Set<PermissionEntity> getAllPermissionsByIdSet(Set<Long> permissionIds);
}
