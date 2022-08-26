package com.juancho.coin.entity;

import java.math.BigDecimal;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.juancho.coin.enums.ECurrency;

import lombok.AllArgsConstructor;
import lombok.Builder;
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

   @ManyToMany(cascade = CascadeType.ALL)
   @JoinTable(name = "user_coin", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "coin_id") })
   private Set<Coin> coinSet;

}
