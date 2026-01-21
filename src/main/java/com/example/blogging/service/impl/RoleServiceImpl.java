package com.example.blogging.service.impl;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleRequestDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.security.DynamicRoleHierarchy;
import com.example.blogging.security.service.RoleHierarchyService;
import com.example.blogging.service.RoleService;
import com.example.blogging.service.exception.conflict.impl.RoleAlreadyExistsException;
import com.example.blogging.service.exception.notFound.impl.RoleNotFoundException;
import com.example.blogging.service.mapper.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;
    private final DynamicRoleHierarchy dynamicRoleHierarchy;
    private final RoleHierarchyService roleHierarchyService;

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
        if (!roleRepository.existsById(roleRequestDto.getParentId())) {
            throw new RoleNotFoundException(roleRequestDto.getParentId());
        }

        RoleEntity parentRoleEntity = roleRepository.getReferenceById(roleRequestDto.getParentId());

        Role role = Role.builder()
                .name(Role.ROLE_PREFIX + roleRequestDto.getName().toUpperCase())
                .parent(roleEntityMapper.toRole(parentRoleEntity))
                .build();

        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException(role.getName());
        }

        RoleEntity roleEntity = roleEntityMapper.toRoleEntity(role);
        roleEntity = roleRepository.saveAndFlush(roleEntity);

        registerHierarchyRefreshSynchronization();

        return roleEntityMapper.toRole(roleEntity);
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

        List<RoleEntity> childrenRoles = roleRepository.findAllByParent(roleToDelete);

        if (!childrenRoles.isEmpty()) {
            RoleEntity parentRole = roleToDelete.getParent();
            for (RoleEntity childRoleEntity : childrenRoles) {
                childRoleEntity.setParent(parentRole);
            }
        }

        roleRepository.deleteById(roleId);
        registerHierarchyRefreshSynchronization();
    }

    private void registerHierarchyRefreshSynchronization() {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                dynamicRoleHierarchy.updateHierarchy(roleHierarchyService.getHierarchy());
            }
        });
    }
}
