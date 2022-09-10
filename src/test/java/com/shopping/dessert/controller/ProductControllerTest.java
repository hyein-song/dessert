package com.shopping.dessert.controller;

import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.service.ProductService;
import com.shopping.dessert.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@MockBean(JpaMetamodelMappingContext.class)
@Import(SecurityConfig.class)
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

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

//    @Test
//    void testAddProduct() {
//    }
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