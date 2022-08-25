package com.juancho.coin.exception;

public class CoinPreConditionException extends RuntimeException {

   public CoinPreConditionException() {
      super("Could not find coin with blank name");
   }
}
