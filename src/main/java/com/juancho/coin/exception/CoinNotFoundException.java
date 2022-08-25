package com.juancho.coin.exception;

public class CoinNotFoundException extends RuntimeException {

   public CoinNotFoundException(Long id) {
      super("Could not find coin " + id);
   }
}
