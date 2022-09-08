package com.shopping.dessert.controller;

import com.shopping.dessert.Custom.CurrentUser;
import com.shopping.dessert.auth.PrincipalDetails;
import com.shopping.dessert.auth.PrincipalDetailsService;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.UserRepository;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
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
    @WithUserDetails(value = "test@google.com", userDetailsServiceBeanName = "principalDetailsService")
    void getUserInfo() throws Exception{

        this.mockMvc.perform(get("/users/myinfo"))
                .andExpect(view().name("user/myinfo"))
                .andExpect(model().attributeExists("myInfoUpdateForm"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

//    @Test
//    void updateUserMyInfo() {
//    }
//
//    @Test
//    void deleteUser() {
//    }
//
//    @Test
//    void testDeleteUser() {
//    }
}