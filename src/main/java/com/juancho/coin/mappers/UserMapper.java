package com.juancho.coin.mappers;

import java.util.List;
import java.util.stream.Collectors;

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
   //@formatter:on
   User fromDto(UserDto dto);

   default List<UserDto> toList(List<User> list) {
      return list.stream().map(this::toDto).collect(Collectors.toList());
   }

   //@formatter:off
   @Mappings({
         @Mapping(target = "id", ignore = true),
         @Mapping(target = "coinSet", ignore = true)})
   //@formatter:on
   User mergeEntity(UserDto dto, @MappingTarget User entity);
}
