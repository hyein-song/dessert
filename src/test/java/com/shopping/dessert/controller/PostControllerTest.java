package com.shopping.dessert.controller;

import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.PostService;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "USER")
    void getPostForm() throws Exception {

        mockMvc.perform(get("/posts/add")
                        .param("productId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/add"));

    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void addPost() throws Exception {

        // given
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("title","제목1");
        params.add("content","내용1");
        params.add("category","review");

        //mocking
        doReturn(1L).when(postService).addPost(any(PostDto.PostAddForm.class),any(UserEntity.class));

        // when & then
        mockMvc.perform(post("/posts/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));

    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void getMyPostsList() throws Exception {

        doReturn(Page.empty()).when(postService).getMyPostList(any(UserEntity.class),any(Pageable.class));

        mockMvc.perform(get("/posts/myPostList"))
                .andExpect(status().isOk())
                .andExpect(view().name("post/myPostList"));

    }

    @Test
    @WithMockUser(roles = "USER")
    void getMyPostDetail() throws Exception {
        PostDto.PostDetail postDetail = PostDto.PostDetail.builder().build();

        doReturn(postDetail).when(postService).getPostDetail(any(Long.class));

        mockMvc.perform(get("/posts/{postId}",1))
                .andExpect(status().isOk())
                .andExpect(view().name("post/detail"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void getUpdatePostForm() throws Exception {
        PostDto.PostDetail postDetail = PostDto.PostDetail.builder().build();

        doReturn(postDetail).when(postService).getPostDetail(any(Long.class));

        mockMvc.perform(get("/posts/update/{postId}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("post/updateForm"));
    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void updatePost() throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("postId","1");
        params.add("title","제목1");
        params.add("content","내용1");
        params.add("category","review");

        doNothing().when(postService).updatePost(any(PostDto.PostUpdateForm.class),any(UserEntity.class));

        mockMvc.perform(post("/posts/update/{postId}",1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/posts/1"));
    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void deletePost() throws Exception {

        doNothing().when(postService).deletePost(any(Long.class),any(UserEntity.class));

        mockMvc.perform(post("/posts/delete/{postId}",1)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("post/deleteSuccess"));
    }
}