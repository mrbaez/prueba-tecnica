package com.juancho.coin.service;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.mapstruct.factory.Mappers;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.dto.UserCoinDto;
import com.juancho.coin.dto.UserDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.entity.User;
import com.juancho.coin.exception.UserNotFoundByUserNameException;
import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;
import com.juancho.coin.exception.UserUniqueUserNameException;
import com.juancho.coin.mappers.CoinMapper;
import com.juancho.coin.mappers.UserMapper;
import com.juancho.coin.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@CacheConfig(cacheNames = "userCache")
public class UserService {

   private static final Integer TOP = 3;

   private final UserRepository userRepository;

   private final CoinService coinService;

   private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

   private final CoinMapper mapperCoin = Mappers.getMapper(CoinMapper.class);


   public UserService(UserRepository userRepository, CoinService coinService) {
      this.userRepository = userRepository;
      this.coinService = coinService;
   }

   public UserDto create(UserDto userDto) {
      if (userRepository.existsByUserName(userDto.getUserName())) {
         throw new UserUniqueUserNameException(userDto.getUserName());
      }
      User user = mapper.fromDto(userDto);
      addCoin(userDto, user);
      return mapper.toDto(userRepository.save(user));
   }

   public UserDto update(UserDto userDto) {
      User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new UserNotFoundException(userDto.getId()));
      if (userRepository.existsByUserNameAndIdNot(userDto.getUserName(), userDto.getId())) {
         throw new UserUniqueUserNameException(userDto.getUserName());
      }
      user = mapper.mergeEntity(userDto, user);
      addCoin(userDto, user);
      return mapper.toDto(userRepository.save(user));
   }

   private void addCoin(UserDto userDto, User user) {
      if (!CollectionUtils.isEmpty(userDto.getCoinSet())) {
         Set<Coin> coinSet = new HashSet<>();
         for (CoinDto coin : userDto.getCoinSet()) {
            coinSet.add(mapperCoin.fromDto(coinService.findById(coin.getId())));
         }
         user.setCoinSet(coinSet);
      }
   }

   public UserDto delete(long l) {
      log.info("trying to delete user {}", l);
      User user = userRepository.findById(l).orElseThrow(() -> new UserNotFoundException(l));
      userRepository.delete(user);
      return mapper.toDto(user);
   }

   @Cacheable
   public List<UserDto> findAll() {
      log.info("finding all users");
      return mapper.toList(userRepository.findAll());
   }

   public UserDto findById(Long id) {
      log.info("finding user with id: {}", id);
      User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
      return mapper.toDto(user);
   }

   public UserDto findByUserName(String userName) {
      log.info("finding user with userName: {}", userName);
      if (userName.isBlank()) {
         throw new UserPreConditionException();
      }
      User user = userRepository.findByUserName(userName).orElseThrow(() -> new UserNotFoundByUserNameException(userName));
      return mapper.toDto(user);
   }

   public List<UserCoinDto> findAllCoins(Long id) {
      log.info("finding coin for user whit id: {}", id);
      UserDto userDto = this.findById(id);
      return userDto
            .getCoinSet()
            .stream()
            .map(x -> new UserCoinDto(x.getName(), x.getRanking(), x.getPriceUsd().multiply(userDto.getTax())))
            .collect(toList());
   }

   public List<UserCoinDto> findTopCoins(Long id, Boolean asc) {
      log.info("finding top 3 coin for user whit id: {}", id);
      UserDto userDto = this.findById(id);
      Stream<UserCoinDto> userCoinDtoList = userDto
            .getCoinSet()
            .stream()
            .map(x -> new UserCoinDto(x.getName(), x.getRanking(), x.getPriceUsd().multiply(userDto.getTax())));
      if (nonNull(asc) && asc) {
         return userCoinDtoList.sorted(Comparator.comparing(UserCoinDto::getLocalCurrencyPrice)).limit(TOP).collect(toList());
      }
      return userCoinDtoList.sorted((x, y) -> y.getLocalCurrencyPrice().compareTo(x.getLocalCurrencyPrice())).limit(TOP).collect(toList());
   }

}
