package com.shopping.dessert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
//@WebMvcTest(controllers = AccountController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@Import(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getRegisterForm() throws Exception{

        UserDto.Request.RegisterForm registerForm = new UserDto.Request.RegisterForm();

        mockMvc.perform(get("/account/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("registerForm"))
                .andExpect(model().attribute("registerForm", registerForm))
                .andDo(print());

    }

    @Test
    void register_Success() throws Exception {

        //given
        UserDto.Request.RegisterForm registerForm = UserDto.Request.RegisterForm
                .builder()
                .email("test@google.com")
                .name("홍길동")
                .phone("01012345678")
                .address("서울")
                .password("Qwerty123!")
                .passwordConfirm("Qwerty123!")
                .build();

        mockMvc.perform(post("/account/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerForm))
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .with(anonymous()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().errorCount(0))
                .andExpect(view().name("user/registerSuccess"));

    }

    @Test
    void login() {
    }
}