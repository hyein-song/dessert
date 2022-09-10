package com.shopping.dessert.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.service.ProductService;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;
    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void addProduct() throws Exception{

        mockMvc.perform(get("/products/add"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productAddForm"))
                .andExpect(view().name("product/add"));

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @Transactional
    void testAddProduct() throws Exception {

        ProductDto.Request.ProductAddForm productAddForm = ProductDto.Request.ProductAddForm
                .builder()
                .name("테스트상품")
                .amount(20L)
                .price(100L)
                .content("상품 상세설명")
                .build();

        doReturn(Optional.empty()).when(productService).getProductByName(productAddForm.getName());
        doReturn(1L).when(productService).addProduct(productAddForm);

        mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productAddForm))
                .with(csrf()))
                .andExpect(status().is3xxRedirection());




    }
//
//    @Test
//    void updateProduct() {
//    }
//
//    @Test
//    void testUpdateProduct() {
//    }
//
//    @Test
//    void deleteProduct() {
//    }
//
//    @Test
//    void getProductList() {
//    }
//
//    @Test
//    void getProductDetail() {
//    }
}