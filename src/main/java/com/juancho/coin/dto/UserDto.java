package com.juancho.coin.dto;

import java.math.BigDecimal;
import java.util.Set;

import com.juancho.coin.entity.Coin;
import com.juancho.coin.enums.ECurrency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {

   private Long id;

   @NonNull
   private String name;

   @NonNull
   private String lastName;

   @NonNull
   private String userName;

   @NonNull
   private String password;

   @NonNull
   private ECurrency localCurrency;

   @NonNull
   private BigDecimal tax;

   private Set<CoinDto> coinSet;
}
