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

import com.juancho.coin.entity.User;
import com.juancho.coin.enums.ECurrency;
import com.juancho.coin.exception.UserNotFoundException;
import com.juancho.coin.exception.UserPreConditionException;
import com.juancho.coin.service.CoinService;
import com.juancho.coin.service.UserService;

@WebMvcTest
@RunWith(SpringRunner.class)
class UserControllerTest {

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
   public void should_GetUseres_valid_request() throws Exception {
      List list = Arrays.asList(User
            .builder()
            .id(1L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build(), User
            .builder()
            .id(2L)
            .name("Marcela")
            .lastName("Ortman")
            .userName("ortmanma")
            .password("clave456")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("1.5"))
            .build());

      when(userService.findAll()).thenReturn(list);

      final ResultActions resultActions = mockMvc.perform(get("/api/user"));
      resultActions.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(1)).andExpect(jsonPath("$[1].id").value(2));
   }

   @Test
   public void should_GetUseres_valid_request_emtpy() throws Exception {
      List list = Arrays.asList();

      when(userService.findAll()).thenReturn(list);

      final ResultActions resultActions = mockMvc.perform(get("/api/user"));
      resultActions.andExpect(status().isOk()).andExpect(content().string("[]"));
   }

   @Test
   public void should_CreateUser_When_ValidRequest() throws Exception {

      when(userService.create(any())).thenReturn(User
            .builder()
            .id(33L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build());
      mockMvc
            .perform(post("/api/user")
                  .contentType(MediaType.APPLICATION_JSON)
                  .content("{ \"name\": \"Roberto\", \"lastName\": \"Baez\", \"userName\": \"baezmr\", \"password\": \"clave123\", \"localCurrency\": "
                        + "\"PESOS_COLOMBIANOS\", \"tax\": 2.5}")
                  .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(33))
            .andExpect(jsonPath("$.name").value("Roberto"));
   }

   @Test
   public void should_UpdateUser_When_ValidRequest() throws Exception {

      when(userService.update(any())).thenReturn(User
            .builder()
            .id(33L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build());
      mockMvc
            .perform(put("/api/user").contentType(MediaType.APPLICATION_JSON).content("{ \"name\": \"user\"}").accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(33))
            .andExpect(jsonPath("$.name").value("Roberto"));
   }

   @Test
   public void should_GetUser_When_ValidRequest() throws Exception {

      User user = User
            .builder()
            .id(1L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();
      when(userService.findById(1L)).thenReturn(user);

      ResultActions resultActions = mockMvc
            .perform(get("/api/user/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Roberto"));
   }

   @Test
   public void should_GetUserByName_When_ValidRequest() throws Exception {

      List<User> list = Stream
            .of(User
                  .builder()
                  .id(1L)
                  .name("Roberto")
                  .lastName("Baez")
                  .userName("baezmr")
                  .password("clave123")
                  .localCurrency(ECurrency.PESOS_COLOMBIANOS)
                  .tax(new BigDecimal("2.5"))
                  .build())
            .collect(Collectors.toList());

      when(userService.findByUserName("Roberto")).thenReturn(list);

      ResultActions resultActions = mockMvc
            .perform(get("/api/user/userName/Roberto"))
            .andExpect(status().isOk())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
   }

   @Test
   public void should_Return412_When_NameIsBlank() throws Exception {

      when(userService.findByUserName(" ")).thenThrow(UserPreConditionException.class);
      mockMvc.perform(get("/api/user/userName/ ").accept(MediaType.APPLICATION_JSON)).andExpect(status().isPreconditionFailed());
   }

   @Test
   public void should_deleteUser_When_ValidRequest() throws Exception {

      User user = User
            .builder()
            .id(1L)
            .name("Roberto")
            .lastName("Baez")
            .userName("baezmr")
            .password("clave123")
            .localCurrency(ECurrency.PESOS_COLOMBIANOS)
            .tax(new BigDecimal("2.5"))
            .build();
      when(userService.delete(1L)).thenReturn(user);

      ResultActions resultActions = mockMvc
            .perform(delete("/api/user/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.name").value("Roberto"));
   }

   @Test
   public void should_Return404_When_UserNotFound() throws Exception {
      when(userService.findById(1L)).thenThrow(UserNotFoundException.class);
      mockMvc.perform(get("/api/user/id/1").accept(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
   }

}
