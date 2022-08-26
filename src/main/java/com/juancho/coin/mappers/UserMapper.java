package com.juancho.coin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.User;

@Mapper
public interface UserMapper {

   UserDto toDto(User entity);

   //@formatter:off
   @Mappings({
         @Mapping(target = "coinSet", ignore = true)})
   //@formatter:oN
   User fromDto(UserDto dto);

   User mergeEntity(UserDto dto, @MappingTarget User entity);
}
