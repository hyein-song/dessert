package com.shopping.dessert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.UserRole;
import com.shopping.dessert.repository.UserRepository;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
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
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
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
        // given
        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("email","test@google.com");
        params.add("name","홍길동");
        params.add("phone","01012345678");
        params.add("address","서울");
        params.add("password","Qwerty123!");
        params.add("passwordConfirm","Qwerty123!");

        //mocking
        doNothing().when(userService).updateMyInfo(any(UserDto.Request.MyInfoUpdateForm.class));

        //when & then
        mockMvc.perform(post("/users/myinfo")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .characterEncoding("UTF-8")
                .with(csrf()))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/myinfo"));
    }

    @Test
    @WithMockUser
    void getDeleteUserForm() throws Exception {

        mockMvc.perform(get("/users/delete"))
                .andExpect(view().name("user/delete"))
                .andExpect(model().attributeExists("userDeleteForm"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void deleteUser() throws Exception {

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("email","test@google.com");
        params.add("password","Qwerty123!");

        doNothing().when(userService).delete(any(UserDto.Request.UserDeleteForm.class));

        mockMvc.perform(post("/users/delete")
                .contentType(MediaType.APPLICATION_JSON)
                .params(params)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(model().errorCount(0))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/users/logout"));

    }
}