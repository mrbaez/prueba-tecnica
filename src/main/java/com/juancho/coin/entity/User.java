package com.juancho.coin.entity;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.juancho.coin.enums.ECurrency;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.CustomLog;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity(name = "user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User {

   @Id
   @GeneratedValue(strategy = IDENTITY)
   @EqualsAndHashCode.Include
   private Long id;

   @NonNull
   private String name;

   @NonNull
   private String lastName;

   @NonNull
   private String userName;

   @NonNull
   @Column(length = 8)
   private String password;

   @NonNull
   private ECurrency localCurrency;

   @NonNull
   private BigDecimal tax;

}
