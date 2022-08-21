package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @PostMapping
    public void addProduct(){

    }

    @GetMapping
    public void getProductsList(){

    }

    @GetMapping("/{productId}")
    public void getProductDetail(@PathVariable Long productId){

    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId){

    }
}
