package com.juancho.coin.exception;

public class UserUniqueUserNameException extends RuntimeException {

   public UserUniqueUserNameException(String userName) {
      super("There is already a User whit the user name: " + userName);
   }
}
