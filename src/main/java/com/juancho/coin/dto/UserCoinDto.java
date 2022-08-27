package com.juancho.coin.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserCoinDto {

   @NonNull
   private String name;

   @NonNull
   private Integer ranking;

   @NonNull
   private BigDecimal localCurrencyPrice;

}
