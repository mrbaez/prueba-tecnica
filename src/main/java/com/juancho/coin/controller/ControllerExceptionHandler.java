package com.juancho.coin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

   @ResponseStatus(HttpStatus.NOT_FOUND)
   @ExceptionHandler(UserNotFoundException.class)
   public void handleNotFound(UserNotFoundException ex) {
      log.error("Requested user not found");
   }

   @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
   @ExceptionHandler(UserPreConditionException.class)
   public void handleBlankName(UserPreConditionException ex) {
      log.error("Precondition fail");
   }
}
