package com.juancho.coin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.User;

@Mapper
public interface UserMapper {

   UserDto toDto(User entity);

   User fromDto(UserDto dto);

   User mergeEntity(UserDto dto, @MappingTarget User entity);
}
