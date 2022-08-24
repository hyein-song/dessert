package com.shopping.dessert.controller;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public String addProduct(@ModelAttribute ProductDto.Request.Add productAdRequest, Model model){
//        ProductDto.Response.ProductDetail savedProduct =  productService.addProduct(productAdRequest);
//        model.addAttribute("product", savedProduct);

        productService.addProduct(productAdRequest);

        return "home"; // detail 페이지로 넘기기
    }
//
//    @GetMapping
//    public String getProductsList(){
//
//    }
//
//    @GetMapping("/{productId}")
//    public String getProductDetail(@PathVariable Long productId){
//
//    }
//
//    @PutMapping("/{productId}")
//    public String updateProduct(@PathVariable Long productId){
//
//    }
}
