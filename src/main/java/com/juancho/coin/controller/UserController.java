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
import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.User;
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
   ResponseEntity<UserDto> update(@RequestBody User user) {
      User created = userService.update(user);
      return ResponseEntity.status(HttpStatus.OK).body(mapper.toDto(created));
   }

   @GetMapping()
   @Loggable
   List<UserDto> all() {
      List<User> users = userService.findAll();
      return users.stream().map(mapper::toDto).collect(toList());
   }

   @GetMapping(value = "/id/{id}")
   UserDto findById(@PathVariable(value = "id") Long id) {
      return mapper.toDto(userService.findById(id));
   }

   @GetMapping(value = "/userName/{userName}")
   List<UserDto> findByUserName(@PathVariable(value = "userName") String userName) {
      List<User> users = userService.findByUserName(userName);
      return users.stream().map(mapper::toDto).collect(toList());
   }

   @DeleteMapping("/id/{id}")
   UserDto delete(@PathVariable(value = "id") Long id) {
      return mapper.toDto(userService.delete(id));
   }
}
