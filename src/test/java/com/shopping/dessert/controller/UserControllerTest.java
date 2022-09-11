package com.shopping.dessert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser
    public void getUserMyPage() throws Exception {

        mockMvc.perform(get("/users/mypage"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/mypage"));

    }

    @Test
    @WithUserDetails(value = "test@google.com", userDetailsServiceBeanName = "principalDetailsService")
    void getUserInfo() throws Exception{

        mockMvc.perform(get("/users/myinfo"))
                .andExpect(view().name("user/myinfo"))
                .andExpect(model().attributeExists("myInfoUpdateForm"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

    @Test
    @WithMockUser
    void updateUserMyInfo() throws Exception {

        UserDto.Request.MyInfoUpdateForm myInfoUpdateForm = UserDto.Request.MyInfoUpdateForm
                .builder()
                .email("test@google.com")
                .name("홍길동")
                .phone("01012345678")
                .address("서울")
                .password("Qwerty123!")
                .passwordConfirm("Qwerty123!")
                .build();

        doNothing().when(userService).updateMyInfo(myInfoUpdateForm);

        mockMvc.perform(post("/users/myinfo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(myInfoUpdateForm))
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(csrf()))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/myinfo"));
    }


    @Test
//    @WithUserDetails(value = "test@google.com", userDetailsServiceBeanName = "principalDetailsService")
    @WithMockUser
    void getDeleteUserForm() throws Exception {

        mockMvc.perform(get("/users/delete"))
                .andExpect(view().name("user/delete"))
                .andExpect(model().attributeExists("userDeleteForm"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test@google.com", userDetailsServiceBeanName = "principalDetailsService")
    void deleteUser() throws Exception {

        UserDto.Request.UserDeleteForm deleteForm = UserDto.Request.UserDeleteForm
                .builder()
                .email("test@google.com")
                .password("Qwerty123!").build();

        doNothing().when(userService).delete(deleteForm);

        mockMvc.perform(post("/users/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteForm))
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/logout"));

    }
}