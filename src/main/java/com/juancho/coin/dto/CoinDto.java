package com.juancho.coin.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class CoinDto {

   private Long id;

   @NonNull
   private String name;

   @NonNull
   private Integer ranking;

   @NonNull
   private BigDecimal priceUsd;

}
