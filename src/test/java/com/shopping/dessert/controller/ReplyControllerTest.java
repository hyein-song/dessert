package com.shopping.dessert.controller;

import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.ReplyDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.ReplyService;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ReplyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyService replyService;

    private UserService userService;

    private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

    @BeforeEach
    public void setUp(){
        params.add("content","내용");
        params.add("postId","1");
    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void addReply() throws Exception{
        //mocking
        doNothing().when(replyService).addReply(any(ReplyDto.class),any(UserEntity.class));

        //when & then
        mockMvc.perform(post("/replies/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));


    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void deleteReply() throws Exception {

        //mocking
        doNothing().when(replyService).deleteReply(any(Long.class),any(UserEntity.class));

        //when & then
        mockMvc.perform(post("/replies/delete/{replyId}",1)
                .params(params)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));



    }
}