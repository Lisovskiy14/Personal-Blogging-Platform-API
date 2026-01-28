package com.example.blogging.service.impl;

import com.example.blogging.domain.Permission;
import com.example.blogging.repository.PermissionRepository;
import com.example.blogging.repository.entity.PermissionEntity;
import com.example.blogging.service.PermissionService;
import com.example.blogging.service.exception.notFound.impl.PermissionNotFoundException;
import com.example.blogging.service.mapper.PermissionEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {
    private final PermissionRepository permissionRepository;
    private final PermissionEntityMapper permissionEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<Permission> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(permissionEntityMapper::toPermission)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Permission> getPermissionsByRoleId(Long roleId) {
        return Set.of();
    }

    @Override
    @Transactional(readOnly = true)
    public Permission getPermissionById(Long permissionId) {
        PermissionEntity permissionEntity = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new PermissionNotFoundException(permissionId));
        return permissionEntityMapper.toPermission(permissionEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<PermissionEntity> getAllPermissionsByIdSet(Set<Long> permissionIds) {
        Set<PermissionEntity> foundPermissions = permissionRepository.findAllByIdIn(permissionIds);
        if (foundPermissions.size() != permissionIds.size()) {
            Set<Long> foundIds = foundPermissions.stream()
                    .map(PermissionEntity::getId)
                    .collect(Collectors.toSet());

            Long wrongId = permissionIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .findFirst()
                    .get();

            throw new PermissionNotFoundException(wrongId);
        }

        return foundPermissions;
    }
}
