package com.juancho.coin.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.juancho.coin.annotations.Loggable;
import com.juancho.coin.dto.UserCoinDto;
import com.juancho.coin.dto.UserDto;
import com.juancho.coin.mappers.UserMapper;
import com.juancho.coin.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

   private final UserService userService;

   private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @PostMapping
   @Loggable
   ResponseEntity<UserDto> create(@RequestBody UserDto user) {
      return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(user));
   }

   @PutMapping()
   ResponseEntity<UserDto> update(@RequestBody UserDto user) {
      return ResponseEntity.status(HttpStatus.OK).body(userService.update(user));
   }

   @GetMapping()
   @Loggable
   List<UserDto> all() {
      return userService.findAll();
   }

   @GetMapping(value = "/id/{id}")
   UserDto findById(@PathVariable(value = "id") Long id) {
      return userService.findById(id);
   }

   @GetMapping(value = "/userCoins/{id}")
   List<UserCoinDto> findAllCoins(@PathVariable(value = "id") Long id) {
      return userService.findAllCoins(id);
   }

   @GetMapping(value = "/topUserCoins")
   List<UserCoinDto> findTopCoins(@RequestParam Long id, @RequestParam(required = false) Boolean asc) {
      return userService.findTopCoins(id, asc);
   }

   @GetMapping(value = "/userName/{userName}")
   UserDto findByUserName(@PathVariable(value = "userName") String userName) {
      return userService.findByUserName(userName);
   }

   @DeleteMapping("/id/{id}")
   UserDto delete(@PathVariable(value = "id") Long id) {
      return userService.delete(id);
   }
}
