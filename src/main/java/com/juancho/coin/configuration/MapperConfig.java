package com.juancho.coin.configuration;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.juancho.coin.mappers.CoinMapper;
import com.juancho.coin.mappers.UserMapper;

@Configuration
public class MapperConfig {

   @Bean
   public UserMapper userMapper() {
      return Mappers.getMapper(UserMapper.class);
   }

   @Bean
   public CoinMapper coinMapper() {
      return Mappers.getMapper(CoinMapper.class);
   }
}
