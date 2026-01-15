package com.example.blogging.service.impl;

import com.example.blogging.domain.User;
import com.example.blogging.dto.user.UserRequestDto;
import com.example.blogging.repository.UserRepository;
import com.example.blogging.repository.entity.UserEntity;
import com.example.blogging.service.UserService;
import com.example.blogging.service.exception.conflict.UserAlreadyExistsException;
import com.example.blogging.service.exception.notFound.UserNotFoundException;
import com.example.blogging.service.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
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
    public User getUserById(UUID id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString()));
        return userEntityMapper.toUser(userEntity);
    }

    @Override
    @Transactional
    public User createUser(UserRequestDto userRequestDto) {
        UserEntity userEntity = UserEntity.builder()
                .username(userRequestDto.getUsername())
                .password("1")
                .build();

        try {
            userEntity = userRepository.saveAndFlush(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistsException(userRequestDto.getUsername());
        }

        return userEntityMapper.toUser(userEntity);
    }
}
