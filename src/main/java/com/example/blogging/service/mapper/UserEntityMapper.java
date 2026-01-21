package com.example.blogging.service.mapper;

import com.example.blogging.domain.User;
import com.example.blogging.repository.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = RoleEntityMapper.class)
public interface UserEntityMapper {
    User toUser(UserEntity userEntity);
}
