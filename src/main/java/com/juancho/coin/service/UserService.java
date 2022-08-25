package com.juancho.coin.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.juancho.coin.entity.User;
import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;
import com.juancho.coin.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserService {

   private final UserRepository userRepository;

   public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
   }

   public User create(User user) {
      return userRepository.save(user);
   }

   public User delete(long l) {
      log.info("trying to delete user {}", l);
      User user = userRepository.findById(l).orElseThrow(() -> new UserNotFoundException(l));
      userRepository.delete(user);
      return user;
   }

   @Cacheable
   public List<User> findAll() {
      log.info("finding all users");
      return userRepository.findAll();
   }

   public User findById(Long id) {
      log.info("finding user with id: {}", id);
      return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
   }

   public List<User> findByUserName(String userName) {
      log.info("finding user with userName: {}", userName);
      if (userName.isBlank()) {
         throw new UserPreConditionException();
      }
      return userRepository.findByUserName(userName);
   }

   public User update(User user) {
      userRepository.findById(user.getId()).orElseThrow(() -> new UserNotFoundException(user.getId()));
      userRepository.save(user);
      return user;
   }
}
