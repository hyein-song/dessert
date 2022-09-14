package com.shopping.dessert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getRegisterForm() throws Exception{

        //when & then
        this.mockMvc.perform(get("/account/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(model().attribute("registerForm", new UserDto.Request.RegisterForm()));
    }

    @Test
    void register_Success() throws Exception {
        //given
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("email","test@google.com");
        params.add("name","홍길동");
        params.add("phone","01012345678");
        params.add("address","서울");
        params.add("password","Qwerty123!");
        params.add("passwordConfirm","Qwerty123!");

        //mocking
        doNothing().when(userService).register(any(UserDto.Request.RegisterForm.class));

        //when & then
        this.mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params)
                        .characterEncoding("UTF-8")
                        .with(csrf())
                        .with(anonymous()))
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("user/registerSuccess"));
    }

    @Test
    void login() throws Exception {

        //when & then
        this.mockMvc.perform(get("/account/login"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginForm"))
                .andExpect(model().attribute("loginForm", new UserDto.Request.LoginForm()))
                .andExpect(view().name("user/login"));

    }


}