package com.shopping.dessert.controller;

import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    @WithMockUser
//    @WithMockUser(username = "john", roles={"ADMIN"})
    public void getUserMyPage() throws Exception {
        //로그인 필요

        this.mockMvc.perform(get("/users/mypage"))
                .andExpect(view().name("mypage"))
                .andExpect(status().isOk())
                .andExpect(authenticated());
    }

//    @Test
//    void getUserInfo() {
//    }
//
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