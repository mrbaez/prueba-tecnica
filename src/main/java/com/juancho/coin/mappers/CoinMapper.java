package com.juancho.coin.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.entity.Coin;

@Mapper
public interface CoinMapper {

   CoinDto toDto(Coin entity);

   Coin fromDto(CoinDto dto);

   Coin mergeEntity(CoinDto dto, @MappingTarget Coin entity);
}
