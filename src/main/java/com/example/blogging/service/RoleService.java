package com.example.blogging.service;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleRequestDto;
import com.example.blogging.dto.role.UpdateRoleRequestDto;

import java.util.Set;

public interface RoleService {
    Set<Role> getAllRoles();
    Role getRoleById(Long roleId);
    Role getRoleByName(String roleName);
    Role createRole(RoleRequestDto roleRequestDto);
    Role updateRole(Long roleId, UpdateRoleRequestDto updateRoleRequestDto);
    void deleteRoleById(Long roleId);
}
