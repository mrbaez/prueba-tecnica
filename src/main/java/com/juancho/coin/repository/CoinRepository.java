package com.juancho.coin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.juancho.coin.entity.Coin;
import com.juancho.coin.entity.User;

@Repository
public interface CoinRepository extends JpaRepository<Coin, Long> {

   List<Coin> findByName(String name);
}
