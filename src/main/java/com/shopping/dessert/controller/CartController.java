package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    @PostMapping
    public void addToCart(){

    }

    @GetMapping
    public void getCartList(){

    }

    @DeleteMapping("/{cartId}")
    public void deleteFromCart(@PathVariable Long cartId){

    }

    @PostMapping("/{cartId}")
    public void updateCart(@PathVariable Long cartId){

    }

}
