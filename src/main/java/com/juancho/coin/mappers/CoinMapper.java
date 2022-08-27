package com.juancho.coin.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.entity.User;

@Mapper
public interface CoinMapper {

   CoinDto toDto(Coin entity);

   Coin fromDto(CoinDto dto);

   default List<CoinDto> toList(List<Coin> list) {
      return list.stream().map(this::toDto).collect(Collectors.toList());
   }

   //@formatter:off
   @Mappings({
         @Mapping(target = "id", ignore = true)})
   //@formatter:on
   Coin mergeEntity(CoinDto dto, @MappingTarget Coin entity);
}
