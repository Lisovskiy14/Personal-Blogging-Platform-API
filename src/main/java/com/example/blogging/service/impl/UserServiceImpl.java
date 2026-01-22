package com.example.blogging.service.impl;

import com.example.blogging.domain.User;
import com.example.blogging.dto.user.UpdateUserRolesRequestDto;
import com.example.blogging.repository.RoleRepository;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.RoleEntity;
import com.example.blogging.repository.entity.UserEntity;
import com.example.blogging.service.UserService;
import com.example.blogging.service.exception.notFound.impl.RoleNotFoundException;
import com.example.blogging.service.exception.notFound.impl.UserNotFoundException;
import com.example.blogging.service.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(UUID userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAnyRole('ADMIN')")
    public User updateUserRoles(UUID userId, UpdateUserRolesRequestDto updateUserRolesRequestDto) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Set<RoleEntity> roleEntitySet = new HashSet<>(
                roleRepository.findAllById(updateUserRolesRequestDto.getRoleIds()));

        if (roleEntitySet.size() != updateUserRolesRequestDto.getRoleIds().size()) {
            Set<Long> foundRoleIds = roleEntitySet.stream()
                    .map(RoleEntity::getId)
                    .collect(Collectors.toSet());

            Long missingRoleId = updateUserRolesRequestDto.getRoleIds().stream()
                    .filter(roleId -> !foundRoleIds.contains(roleId))
                    .findFirst()
                    .get();

            throw new RoleNotFoundException(missingRoleId);
        }

        userEntity.getRoles().clear();
        userEntity.getRoles().addAll(roleEntitySet);

        userEntity = userRepository.saveAndFlush(userEntity);
        log.info("User with id {} was updated with new roles: {}.", userId, roleEntitySet.stream()
                .map(RoleEntity::getName)
                .toList());
        return userEntityMapper.toUser(userEntity);
    }
}
