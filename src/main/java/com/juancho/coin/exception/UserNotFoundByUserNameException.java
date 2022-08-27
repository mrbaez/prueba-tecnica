package com.juancho.coin.exception;

public class UserNotFoundByUserNameException extends RuntimeException {

   public UserNotFoundByUserNameException(String userName) {
      super("Could not find user name " + userName);
   }
}
