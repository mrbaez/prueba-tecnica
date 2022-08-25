package com.juancho.coin.entity;

import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@Entity(name = "coin")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Coin {

   @Id
   @GeneratedValue(strategy = IDENTITY)
   @EqualsAndHashCode.Include
   private Long id;

   @NonNull
   private String name;

   @NonNull
   private Integer ranking;

   @NonNull
   private BigDecimal priceUsd;

}

