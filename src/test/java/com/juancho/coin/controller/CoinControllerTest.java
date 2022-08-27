package com.juancho.coin.controller;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import com.juancho.coin.dto.CoinDto;
import com.juancho.coin.entity.Coin;
import com.juancho.coin.exception.CoinNotFoundException;
import com.juancho.coin.exception.CoinPreConditionException;
import com.juancho.coin.service.CoinService;
import com.juancho.coin.service.UserService;

@WebMvcTest
@RunWith(SpringRunner.class)
public class CoinControllerTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private WebApplicationContext webApplicationContext;

   @MockBean
   private CoinService coinService;

   @MockBean
   private UserService userService;

   @Before
   public void setUp() {
      this.mockMvc = webAppContextSetup(webApplicationContext).build();
   }

   @Test
   public void should_GetCoines_valid_request() throws Exception {
      List list = Arrays.asList(Coin.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build(),
            Coin.builder().id(2L).name("ETHEREUM").ranking(2).priceUsd(new BigDecimal(1500)).build());

      when(coinService.findAll()).thenReturn(list);

      final ResultActions resultActions = mockMvc.perform(get("/api/coin"));
      resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[1].id").value(2));
   }

   @Test
   public void should_GetCoines_valid_request_emtpy() throws Exception {
      List list = Arrays.asList();

      when(coinService.findAll()).thenReturn(list);

      final ResultActions resultActions = mockMvc.perform(get("/api/coin"));
      resultActions.andExpect(status().isOk()).andExpect(content().string("[]"));
   }

   @Test
   public void should_CreateCoin_When_ValidRequest() throws Exception {

      when(coinService.create(any())).thenReturn(CoinDto.builder().id(33L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build());
      mockMvc
            .perform(post("/api/coin")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{ \"name\": \"BITCOIN\", \"ranking\": 1, \"priceUsd\": 20000}")
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(33))
            .andExpect(jsonPath("$.name").value("BITCOIN"));
   }

   @Test
   public void should_UpdateCoin_When_ValidRequest() throws Exception {

      when(coinService.update(any())).thenReturn(CoinDto.builder().id(33L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build());
      mockMvc
            .perform(put("/api/coin").contentType(MediaType.APPLICATION_JSON) .content("{ \"name\": \"BITCOIN\", \"ranking\": 1, \"priceUsd\": 20000}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(33))
            .andExpect(jsonPath("$.name").value("BITCOIN"));
   }

   @Test
   public void should_GetCoin_When_ValidRequest() throws Exception {

      CoinDto coin = CoinDto.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();
      when(coinService.findById(1L)).thenReturn(coin);

      ResultActions resultActions = mockMvc
            .perform(get("/api/coin/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("BITCOIN"));
   }

   @Test
   public void should_GetCoinByName_When_ValidRequest() throws Exception {

      List<CoinDto> list = Stream
            .of(CoinDto.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build())
            .collect(Collectors.toList());

      when(coinService.findByCoinName("BITCOIN")).thenReturn(list);

      ResultActions resultActions = mockMvc
            .perform(get("/api/coin/coinName/BITCOIN"))
            .andExpect(status().isOk())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
   }

   @Test
   public void should_Return412_When_NameIsBlank() throws Exception {

      when(coinService.findByCoinName(" ")).thenThrow(CoinPreConditionException.class);
      mockMvc.perform(get("/api/coin/coinName/ ").accept(MediaType.APPLICATION_JSON)).andExpect(status().isPreconditionFailed());
   }

   @Test
   public void should_deleteCoin_When_ValidRequest() throws Exception {

      CoinDto coin = CoinDto.builder().id(1L).name("BITCOIN").ranking(1).priceUsd(new BigDecimal(20000)).build();
      when(coinService.delete(1L)).thenReturn(coin);

      ResultActions resultActions = mockMvc
            .perform(delete("/api/coin/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("BITCOIN"));
   }

   @Test
   public void should_Return404_When_CoinNotFound() throws Exception {
      when(coinService.findById(1L)).thenThrow(CoinNotFoundException.class);
      mockMvc.perform(get("/api/coin/id/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
   }

}
