package com.example.blogging.security;

import com.example.blogging.common.Permission;
import com.example.blogging.repository.PermissionRepository;
import com.example.blogging.repository.entity.PermissionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class SavedPermissionsValidator {
    private final PermissionRepository permissionRepository;

    @Transactional
    public void validate() {
        Permission[] permissions = Permission.values();

        long count = permissionRepository.count();
        if (count == permissions.length) {
            log.info("All permissions are already saved.");
            return;
        }

        Set<PermissionEntity> permissionEntities = Arrays.stream(permissions)
                .map(permission -> PermissionEntity.builder()
                        .name(permission.getValue())
                        .build())
                .collect(Collectors.toSet());

        if (count == 0) {
            permissionRepository.saveAll(permissionEntities);
        } else {
            List<String> savedNames = permissionRepository.findAll().stream()
                    .map(PermissionEntity::getName)
                    .toList();

            Set<PermissionEntity> permissionsToSave = permissionEntities.stream()
                    .filter(permission -> !savedNames.contains(permission.getName()))
                    .collect(Collectors.toSet());

            permissionRepository.saveAll(permissionsToSave);
        }

        log.info("Missed permissions were saved.");
    }
}
