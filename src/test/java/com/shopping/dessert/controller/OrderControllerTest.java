package com.shopping.dessert.controller;

import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.OrderDto;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.CartService;
import com.shopping.dessert.service.OrderService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
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
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;
    @MockBean
    private CartService cartService;
    @MockBean
    private UserService userService;

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void orderProc() throws Exception{

        //given
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

        //mocking
        doReturn(cartDetailForms).when(cartService).getCartlist(any(UserEntity.class));

        //when & then
        mockMvc.perform(get("/orders/proc"))
                .andExpect(status().isOk())
                .andExpect(view().name("order/proc"));


    }

    @Test
    @WithUserDetails(value = "test@google.com", setupBefore = TestExecutionEvent.TEST_EXECUTION, userDetailsServiceBeanName = "principalDetailsService")
    void addOrder() throws Exception {

        //given
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

        OrderDto.OrderProcDto orderProcDto = OrderDto.OrderProcDto
                .builder()
                .itemsPriceSum(3000L)
                .shippingPrice(3000)
                .totalPrice(6000L)
                .payment("directcheck")
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("itemsPriceSum","2000");
        params.add("shippingPrice","3000");
        params.add("totalPrice","5000");
        params.add("payment","directcheck");

        //mocking
        doReturn(cartDetailForms).when(cartService).getCartlist(any(UserEntity.class));
        doReturn(1L).when(orderService).addOrder(orderProcDto,cartDetailForms,1L);
        doNothing().when(cartService).deleteFromCart(1L);

        // when & then
        mockMvc.perform(post("/orders/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("order/orderSuccess"))
                .andExpect(model().errorCount(0));
    }

    @Test
    void getOrdersList() {
    }

    @Test
    void getOrderProductList() {
    }
}