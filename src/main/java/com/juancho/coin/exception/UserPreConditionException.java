package com.juancho.coin.exception;

public class UserPreConditionException extends RuntimeException {

   public UserPreConditionException() {
      super("Could not find user with blank userName");
   }
}
