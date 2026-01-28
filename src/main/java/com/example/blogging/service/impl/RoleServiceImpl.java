package com.example.blogging.service.impl;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleRequestDto;
import com.example.blogging.dto.role.UpdateRoleRequestDto;
import com.example.blogging.repository.PermissionRepository;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.entity.PermissionEntity;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.security.DynamicRoleHierarchy;
import com.example.blogging.security.service.RoleHierarchyService;
import com.example.blogging.service.PermissionService;
import com.example.blogging.service.RoleService;
import com.example.blogging.service.exception.conflict.impl.RoleAlreadyExistsException;
import com.example.blogging.service.exception.notFound.impl.PermissionNotFoundException;
import com.example.blogging.service.exception.notFound.impl.RoleNotFoundException;
import com.example.blogging.service.exception.roleHierarchy.IllegalRoleHierarchyException;
import com.example.blogging.service.exception.roleHierarchy.impl.RecursiveHierarchyException;
import com.example.blogging.service.exception.roleHierarchy.impl.RootRoleConflictException;
import com.example.blogging.service.mapper.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final DynamicRoleHierarchy dynamicRoleHierarchy;
    private final RoleHierarchyService roleHierarchyService;
    private final PermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Set<Role> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleEntityMapper::toRole)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Role getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
        return roleEntityMapper.toRole(roleEntity);
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Role getRoleByName(String roleName) {
        RoleEntity roleEntity = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        return roleEntityMapper.toRole(roleEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Role createRole(RoleRequestDto roleRequestDto) {
        String formatterRoleName = Role.ROLE_PREFIX + roleRequestDto.getName().toUpperCase();

        if (roleRepository.existsByName(formatterRoleName)) {
            throw new RoleAlreadyExistsException(formatterRoleName);
        }

        RoleEntity parent = (roleRequestDto.getParentId() != null)
                ? roleRepository.findById(roleRequestDto.getParentId())
                        .orElseThrow(() -> new RoleNotFoundException(roleRequestDto.getParentId()))
                : null;

        RoleEntity child = (roleRequestDto.getChildId() != null)
                ? roleRepository.findById(roleRequestDto.getChildId())
                        .orElseThrow(() -> new RoleNotFoundException(roleRequestDto.getChildId()))
                : null;

        if (parent == null) {
            RoleEntity existingRoot = roleRepository.findByParent(null);
            if (existingRoot != null && (child == null || !existingRoot.getId().equals(child.getId()))) {
                throw new RootRoleConflictException(existingRoot.getId());
            }
        }

        if (parent != null && child != null) {
            if (child.getParent() == null || !child.getParent().getId().equals(parent.getId())) {
                throw new IllegalRoleHierarchyException("Provided parent is not the actual parent of the provided child");
            }
            if (child.getId().equals(parent.getId())) {
                throw new IllegalRoleHierarchyException("Provided parent and child are the same");
            }
        }

        // Check if all given permissions exist
        Set<PermissionEntity> foundPermissions = permissionService
                .getAllPermissionsByIdSet(roleRequestDto.getPermissionIds());

        // Creating a new role
        RoleEntity newRole = RoleEntity.builder()
                .name(formatterRoleName)
                .permissions(foundPermissions)
                .parent(parent)
                .children(new HashSet<>())
                .build();

        if (child != null) {
            if (parent != null) {
                parent.getChildren().remove(child);
                parent.getChildren().add(newRole);
            }
            newRole.getChildren().add(child);
            child.setParent(newRole);
        } else if (parent != null) {
            parent.getChildren().add(newRole);
        }

        newRole = roleRepository.saveAndFlush(newRole);

        registerHierarchyRefreshSynchronization();

        log.info("New Role was created: {}.", newRole.getId());
        return roleEntityMapper.toRole(newRole);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Role updateRole(Long roleId, UpdateRoleRequestDto updateRoleRequestDto) {
        RoleEntity role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
        
        if (updateRoleRequestDto.getName() != null) {
            String newRoleName = Role.ROLE_PREFIX + updateRoleRequestDto.getName().toUpperCase();
            if (!newRoleName.equals(role.getName()) && roleRepository.existsByName(newRoleName)) {
                throw new RoleAlreadyExistsException(newRoleName);
            }
            role.setName(newRoleName);
        }

        if (updateRoleRequestDto.getParentId() != null) {
            Long parentId = updateRoleRequestDto.getParentId();

            if (parentId.equals(roleId)) {
                throw new IllegalRoleHierarchyException("Cannot assign role to itself as a parent.");
            }

            RoleEntity parent = roleRepository.findById(parentId)
                    .orElseThrow(() -> new RoleNotFoundException(parentId));

            if (hasRecursion(role.getId(), parent)) {
                throw new RecursiveHierarchyException();
            }

            if (role.getParent() != null) {
                role.getParent().getChildren().remove(role);
            }

            role.setParent(parent);
            parent.getChildren().add(role);
        }

        Set<Long> permissionIds = updateRoleRequestDto.getPermissionIds();
        if (permissionIds != null && !permissionIds.isEmpty()) {

            Set<PermissionEntity> foundPermissions = permissionService
                    .getAllPermissionsByIdSet(permissionIds);

            role.setPermissions(foundPermissions);
        }

        role = roleRepository.saveAndFlush(role);

        registerHierarchyRefreshSynchronization();
        return roleEntityMapper.toRole(role);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteRoleById(Long roleId) {
        RoleEntity roleToDelete = roleRepository.findById(roleId)
                .orElse(null);

        if (roleToDelete == null) {
            return;
        }

        RoleEntity parent = roleToDelete.getParent();

        if (roleToDelete.getChildren() != null && !roleToDelete.getChildren().isEmpty()) {
            if (parent == null && roleToDelete.getChildren().size() > 1) {
                throw new IllegalRoleHierarchyException("Cannot delete root role with 2 or more child roles.");
            }
            roleToDelete.getChildren().forEach(child -> {
                child.setParent(parent);
                if (parent != null) {
                    parent.getChildren().add(child);
                }
            });
        }

        if (parent != null) {
            parent.getChildren().remove(roleToDelete);
        }

        roleRepository.delete(roleToDelete);
        roleRepository.flush();

        registerHierarchyRefreshSynchronization();
        log.info("Role with id {} was deleted.", roleId);
    }

    private void registerHierarchyRefreshSynchronization() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                dynamicRoleHierarchy.updateHierarchy(roleHierarchyService.getHierarchy());
            }
        });
    }

    private boolean hasRecursion(Long roleIdToUpdate, RoleEntity potentialParent) {
        RoleEntity current = potentialParent;
        while (current != null) {
            if (current.getId().equals(roleIdToUpdate)) {
                return true;
            }
            current = current.getParent();
        }
        return false;
    }
}
