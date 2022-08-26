package com.juancho.coin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.juancho.coin.exception.CoinNotFoundException;
import com.juancho.coin.exception.CoinPreConditionException;
import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;
import com.juancho.coin.exception.UserUniqueUserNameException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(UserNotFoundException.class)
   public void handleUserNotFound(UserNotFoundException ex) {
      log.error("Requested user not found");
   }

   @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
   @ExceptionHandler(UserUniqueUserNameException.class)
   public void handleUserUniqueUserName(UserUniqueUserNameException ex) {
      log.error("There is already a User whit the user name");
   }

   @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
   @ExceptionHandler(UserPreConditionException.class)
   public void handleUserBlankUserName(UserPreConditionException ex) {
      log.error("Precondition fail");
   }

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(CoinNotFoundException.class)
   public void handleCoinNotFound(CoinNotFoundException ex) {
      log.error("Requested coin not found");
   }

   @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
   @ExceptionHandler(CoinPreConditionException.class)
   public void handleCoinBlankName(CoinPreConditionException ex) {
      log.error("Precondition fail");
   }
}
