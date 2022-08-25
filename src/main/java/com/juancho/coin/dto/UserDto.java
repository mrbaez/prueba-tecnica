package com.juancho.coin.dto;

import java.math.BigDecimal;

import com.juancho.coin.enums.ECurrency;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
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
}
