package com.juancho.coin.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.exception.CoinNotFoundException;
import com.juancho.coin.exception.CoinPreConditionException;
import com.juancho.coin.mappers.CoinMapper;
import com.juancho.coin.repository.CoinRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "coinCache")
public class CoinService {

   private final CoinRepository coinRepository;

   private final CoinMapper coinMapper;

   public CoinDto create(CoinDto coinDto) {
      Coin coin = coinMapper.fromDto(coinDto);
      return coinMapper.toDto(coinRepository.save(coin));
   }

   public CoinDto delete(long l) {
      log.info("trying to delete coin {}", l);
      Coin coin = coinRepository.findById(l).orElseThrow(() -> new CoinNotFoundException(l));
      coinRepository.delete(coin);
      return coinMapper.toDto(coin);
   }

   @Cacheable
   public List<CoinDto> findAll() {
      log.info("finding all coins");
      return coinMapper.toList(coinRepository.findAll());
   }

   public CoinDto findById(Long id) {
      log.info("finding coin with id: {}", id);
      Coin coin = coinRepository.findById(id).orElseThrow(() -> new CoinNotFoundException(id));
      return coinMapper.toDto(coin);
   }

   public List<CoinDto> findByCoinName(String coinName) {
      log.info("finding coin with coinName: {}", coinName);
      if (coinName.isBlank()) {
         throw new CoinPreConditionException();
      }
      return coinMapper.toList(coinRepository.findByName(coinName));
   }

   public CoinDto update(CoinDto coinDto) {
      Coin coin = coinRepository.findById(coinDto.getId()).orElseThrow(() -> new CoinNotFoundException(coinDto.getId()));
      coin = coinMapper.mergeEntity(coinDto, coin);
      return coinMapper.toDto(coinRepository.save(coin));
   }
}
