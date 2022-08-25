package com.juancho.coin.controller;

import static java.util.stream.Collectors.toList;

import java.util.List;

import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.juancho.coin.annotations.Loggable;
import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.mappers.CoinMapper;
import com.juancho.coin.service.CoinService;

@RestController
@RequestMapping("/api/coin")
public class CoinController {

   private final CoinService coinService;

   private final CoinMapper mapper = Mappers.getMapper(CoinMapper.class);

   public CoinController(CoinService coinService) {
      this.coinService = coinService;
   }

   @PostMapping
   @Loggable
   ResponseEntity<CoinDto> create(@RequestBody CoinDto coin) {
      Coin created = coinService.create(mapper.fromDto(coin));
      return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(created));
   }

   @PutMapping()
   ResponseEntity<CoinDto> update(@RequestBody Coin coin) {
      Coin created = coinService.update(coin);
      return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(created));
   }

   @GetMapping()
   @Loggable
   List<CoinDto> all() {
      List<Coin> coins = coinService.findAll();
      return coins.stream().map(mapper::toDto).collect(toList());
   }

   @GetMapping(value = "/id/{id}")
   CoinDto findByCoinName(@PathVariable(value = "id") Long id) {
      return mapper.toDto(coinService.findById(id));
   }

   @GetMapping(value = "/coinName/{coinName}")
   List<CoinDto> findByCoinName(@PathVariable(value = "coinName") String coinName) {
      List<Coin> coins = coinService.findByCoinName(coinName);
      return coins.stream().map(mapper::toDto).collect(toList());
   }

   @DeleteMapping("/id/{id}")
   CoinDto delete(@PathVariable(value = "id") Long id) {
      return mapper.toDto(coinService.delete(id));
   }
}
