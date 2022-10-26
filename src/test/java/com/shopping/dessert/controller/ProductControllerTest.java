package com.shopping.dessert.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dessert.config.SecurityConfig;
import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.service.ProductService;
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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    void testAddProduct() throws Exception {

        MultiValueMap<String,String> params = new LinkedMultiValueMap<>();
        params.add("name","테스트상품");
        params.add("amount","20");
        params.add("price","100");
        params.add("content","상품 상세설명");


        doReturn(Optional.empty()).when(productService).getProductByName(any(String.class));
        doReturn(1L).when(productService).addProduct(any(ProductDto.ProductAddForm.class));

        mockMvc.perform(post("/products/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .accept(MediaType.APPLICATION_JSON)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateProduct() throws Exception {
        Long productId = 1L;
        ProductDto.ProductDetail productDetail = ProductDto.ProductDetail.builder().build();

        doReturn(productDetail).when(productService).getProductDetail(productId);

        mockMvc.perform(get("/products/update/{productId}",productId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("product/update"));
    }
//
    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateProduct() throws Exception{
        Long productId = 1L;
        ProductDto.ProductDetail productDetail = ProductDto.ProductDetail
                .builder()
                .productId(1L)
                .name("테스트상품")
                .amount(20L)
                .price(100L)
                .content("상품 상세설명")
                .build();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("postId","1");
        params.add("name","테스트상품");
        params.add("amount","20");
        params.add("price","100");
        params.add("content","상품 상세설명");

        doReturn(Optional.empty()).when(productService).getProductByName(productDetail.getName());
        doNothing().when(productService).updateProduct(productDetail);

        mockMvc.perform(post("/products/update/{productId}",productId)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .params(params)
                .with(csrf()))
                .andExpect(status().is3xxRedirection());

}

    @Test
    @WithMockUser(roles = "ADMIN")
    void deleteProduct() throws Exception {

        Long productId = 1L;
        doNothing().when(productService).deleteProduct(productId);

        mockMvc.perform(get("/products/delete/{productId}",productId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

    }

    @Test
    void getProductList() throws Exception {

//        ProductDto.ProductDetail productDetail = ProductDto.ProductDetail
//                .builder()
//                .productId(1L)
//                .name("테스트상품")
//                .amount(20L)
//                .price(100L)
//                .content("상품 상세설명")
//                .build();
//
//        List<ProductDto.ProductDetail> detailList = new ArrayList<>();
//        detailList.add(productDetail);
//
//        Pageable pageable =  PageRequest.of(3, 3);
//        Page<ProductDto.ProductDetail> productDetails = new PageImpl<>(detailList,pageable,100);

        doReturn(Page.empty()).when(productService).getProductList(any(Pageable.class));

        mockMvc.perform(get("/products/list"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("productDetails"))
                .andExpect(view().name("product/list"));

    }

    @Test
    void getProductDetail() throws Exception {
        Long productId = 1L;
        ProductDto.ProductDetail productDetail = ProductDto.ProductDetail
                .builder()
                .productId(1L)
                .name("테스트상품")
                .amount(20L)
                .price(100L)
                .content("상품 상세설명")
                .build();

        doReturn(productDetail).when(productService).getProductDetail(productId);

        mockMvc.perform(get("/products/{productId}",productId))
                .andExpect(status().isOk());
//                .andExpect(model().attributeExists("productDetail","cartAddForm"))
//                .andExpect(view().name("product/detail"));
    }
}