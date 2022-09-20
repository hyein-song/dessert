package com.shopping.dessert.controller;

import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.CartService;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

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
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @MockBean
    private UserService userService;

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void addToCart() throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("productId","1");
        params.add("amount","2000");

        doNothing().when(cartService).addToCart(any(CartDto.Response.CartAddForm.class),any(UserEntity.class));

        mockMvc.perform(post("/carts/add")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carts/list"));

    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void getCartList() throws Exception {

        //given
        List<CartDto.Response.CartDetailForm> cartDetailForms = getCartDetailFormList();

        doReturn(cartDetailForms).when(cartService).getCartlist(any(UserEntity.class));

        mockMvc.perform(get("/carts/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("cart/list"));
    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void deleteFromCart() throws Exception {

        //given
        Long cartId = 1L;

        //mocking
        doNothing().when(cartService).deleteFromCart(cartId);

        mockMvc.perform(post("/carts/delete/{cartId}", cartId)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/carts/list"));

    }


    List<CartDto.Response.CartDetailForm> getCartDetailFormList(){
        ProductDto.ProductDetail productDetail = ProductDto.ProductDetail
                .builder()
                .productId(1L)
                .name("테스트상품")
                .amount(20L)
                .price(100L)
                .content("상품 상세설명")
                .build();

        CartDto.Response.CartDetailForm cartDetailForm = CartDto.Response.CartDetailForm
                .builder()
                .cartId(1L)
                .amount(200L)
                .totalPrice(200L)
                .productDetail(productDetail)
                .build();

        List<CartDto.Response.CartDetailForm> cartDetailForms = new ArrayList<>();
        cartDetailForms.add(cartDetailForm);

        return cartDetailForms;
    }
}