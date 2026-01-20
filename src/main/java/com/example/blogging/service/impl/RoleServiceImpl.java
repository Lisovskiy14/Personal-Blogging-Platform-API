package com.example.blogging.service.impl;

import com.example.blogging.domain.Role;
import com.example.blogging.dto.role.RoleRequestDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.service.RoleService;
import com.example.blogging.service.exception.conflict.impl.RoleAlreadyExistsException;
import com.example.blogging.service.exception.notFound.impl.RoleNotFoundException;
import com.example.blogging.service.mapper.RoleEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleEntityMapper::toRole)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException(roleId));
        return roleEntityMapper.toRole(roleEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String roleName) {
        RoleEntity roleEntity = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleNotFoundException(roleName));
        return roleEntityMapper.toRole(roleEntity);
    }

    @Override
    @Transactional
    public Role createRole(RoleRequestDto roleRequestDto) {
        Role role = Role.builder()
                .name(Role.ROLE_PREFIX + roleRequestDto.getName())
                .build();

        if (roleRepository.existsByName(role.getName())) {
            throw new RoleAlreadyExistsException(role.getName());
        }

        RoleEntity roleEntity = roleEntityMapper.toRoleEntity(role);
        roleEntity = roleRepository.saveAndFlush(roleEntity);

        return roleEntityMapper.toRole(roleEntity);
    }

    @Override
    @Transactional
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}
