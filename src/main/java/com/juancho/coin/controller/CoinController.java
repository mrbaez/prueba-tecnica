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

   public CoinController(CoinService coinService) {
      this.coinService = coinService;
   }

   @PostMapping
   @Loggable
   ResponseEntity<CoinDto> create(@RequestBody CoinDto coin) {
      return ResponseEntity.status(HttpStatus.CREATED).body(coinService.create(coin));
   }

   @PutMapping()
   ResponseEntity<CoinDto> update(@RequestBody CoinDto coin) {
      return ResponseEntity.status(HttpStatus.CREATED).body(coinService.update(coin));
   }

   @GetMapping()
   @Loggable
   List<CoinDto> all() {
      return coinService.findAll();
   }

   @GetMapping(value = "/id/{id}")
   CoinDto findById(@PathVariable(value = "id") Long id) {
      return coinService.findById(id);
   }

   @GetMapping(value = "/coinName/{coinName}")
   List<CoinDto> findByCoinName(@PathVariable(value = "coinName") String coinName) {
      return coinService.findByCoinName(coinName);
   }

   @DeleteMapping("/id/{id}")
   CoinDto delete(@PathVariable(value = "id") Long id) {
      return coinService.delete(id);
   }
}
