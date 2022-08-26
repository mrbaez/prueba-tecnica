package com.juancho.coin.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.entity.User;
import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;
import com.juancho.coin.exception.UserUniqueUserNameException;
import com.juancho.coin.mappers.UserMapper;
import com.juancho.coin.repository.CoinRepository;
import com.juancho.coin.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserService {

   private final UserRepository userRepository;

   private final CoinRepository coinRepository;

   private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

   public UserService(UserRepository userRepository, CoinRepository coinRepository) {
      this.userRepository = userRepository;
      this.coinRepository = coinRepository;
   }

   public UserDto create(UserDto userDto) {
      if (userRepository.existsByUserName(userDto.getUserName())) {
         throw new UserUniqueUserNameException(userDto.getUserName());
      }
      User user = mapper.fromDto(userDto);
      if (!CollectionUtils.isEmpty(userDto.getCoinSet())) {
         Set<Coin> coinSet = new HashSet<>();
         for (CoinDto coin : userDto.getCoinSet()) {
            Optional<Coin> coinOptional = coinRepository.findById(coin.getId());
            coinOptional.ifPresent(coinSet::add);
         }
         user.setCoinSet(coinSet);
      }
      return mapper.toDto(userRepository.save(user));
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
