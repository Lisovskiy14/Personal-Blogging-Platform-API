package com.example.blogging.security.service.impl;

import com.example.blogging.repository.RoleRepository;
import com.example.blogging.security.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleHierarchyServiceImpl implements RoleHierarchyService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public String getHierarchy() {
        return roleRepository.findAll().stream()
                .filter(roleEntity -> roleEntity.getParent() != null)
                .map(roleEntity -> roleEntity.getParent().getName() + " > " + roleEntity.getName())
                .collect(Collectors.joining(" \n"));
    }
}
