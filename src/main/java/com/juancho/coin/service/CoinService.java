package com.juancho.coin.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.juancho.coin.entity.Coin;
import com.juancho.coin.exception.CoinNotFoundException;
import com.juancho.coin.exception.CoinPreConditionException;
import com.juancho.coin.repository.CoinRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "coinCache")
public class CoinService {

   private final CoinRepository coinRepository;

   public CoinService(CoinRepository coinRepository) {
      this.coinRepository = coinRepository;
   }

   public Coin create(Coin coin) {
      return coinRepository.save(coin);
   }

   public Coin delete(long l) {
      log.info("trying to delete coin {}", l);
      Coin coin = coinRepository.findById(l).orElseThrow(() -> new CoinNotFoundException(l));
      coinRepository.delete(coin);
      return coin;
   }

   @Cacheable
   public List<Coin> findAll() {
      log.info("finding all coins");
      return coinRepository.findAll();
   }

   public Coin findById(Long id) {
      log.info("finding coin with id: {}", id);
      return coinRepository.findById(id).orElseThrow(() -> new CoinNotFoundException(id));
   }

   public List<Coin> findByCoinName(String coinName) {
      log.info("finding coin with coinName: {}", coinName);
      if (coinName.isBlank()) {
         throw new CoinPreConditionException();
      }
      return coinRepository.findByName(coinName);
   }

   public Coin update(Coin coin) {
      coinRepository.findById(coin.getId()).orElseThrow(() -> new CoinNotFoundException(coin.getId()));
      coinRepository.save(coin);
      return coin;
   }
}
