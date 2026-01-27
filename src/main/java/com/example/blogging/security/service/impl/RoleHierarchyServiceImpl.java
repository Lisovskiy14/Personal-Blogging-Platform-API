package com.example.blogging.security.service.impl;

import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.security.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public String getHierarchy() {
        Set<RoleEntity> allRoles = roleRepository.findAllWithPermissions();

        return allRoles.stream()
                .map(role -> {
                    StringBuilder sb = new StringBuilder();

                    if (role.getParent() != null) {
                        sb
                                .append(role.getParent().getName())
                                .append(" > ")
                                .append(role.getName())
                                .append("\n");
                    }

                    role.getPermissions().forEach(permission -> sb
                            .append(role.getName())
                            .append(" > ")
                            .append(permission.getAuthority())
                            .append("\n")
                    );

                    return sb.toString();
                })
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(""));
    }
}
