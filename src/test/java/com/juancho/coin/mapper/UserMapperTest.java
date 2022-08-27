package com.juancho.coin.mapper;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.User;
import com.juancho.coin.enums.ECurrency;
import com.juancho.coin.mappers.UserMapper;

public class UserMapperTest {

   private static final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

   @Test
   void test_mapper_to_userDto() {
      User user = User
            .builder()
            .id(1L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();
      UserDto userDto = MAPPER.toDto(user);

      assertThat(user.getId()).isEqualTo(userDto.getId());
      assertThat(user.getName()).isEqualTo(userDto.getName());
   }

   @Test
   void test_mapper_from_userDto() {
      UserDto userDto = UserDto
            .builder()
            .id(33L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();
      User user = MAPPER.fromDto(userDto);

      assertThat(user.getId()).isEqualTo(userDto.getId());
      assertThat(user.getName()).isEqualTo(userDto.getName());
   }

   @Test
   void test_mapper_merge_user() {
      UserDto userDto = UserDto
            .builder()
            .id(33L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();

      User user = User
            .builder()
            .id(1L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();
      User user1 = MAPPER.mergeEntity(userDto, user);

      assertThat(user1.getId()).isEqualTo(1L);
      assertThat(user1.getName()).isEqualTo("Roberto");
   }
}
