package com.juancho.coin.mapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.mappers.CoinMapper;

public class CoinMapperTest {

   private static final CoinMapper MAPPER = Mappers.getMapper(CoinMapper.class);

   @Test
   void test_mapper_to_coinDto() {
      Coin coin = Coin.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();
      CoinDto coinDto = MAPPER.toDto(coin);

      assertThat(coin.getId()).isEqualTo(coinDto.getId());
      assertThat(coin.getName()).isEqualTo(coinDto.getName());
   }

   @Test
   void test_mapper_from_coinDto() {
      CoinDto coinDto = CoinDto.builder().id(33L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();
      Coin coin = MAPPER.fromDto(coinDto);

      assertThat(coin.getId()).isEqualTo(coinDto.getId());
      assertThat(coin.getName()).isEqualTo(coinDto.getName());
   }

   @Test
   void test_mapper_merge_coin() {
      CoinDto coinDto = CoinDto.builder().id(33L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();

      Coin coin = Coin.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();
      Coin coin1 = MAPPER.mergeEntity(coinDto, coin);

      assertThat(coin1.getId()).isEqualTo(1L);
      assertThat(coin1.getName()).isEqualTo("BITCOIN");
   }
}
