package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    @PostMapping
    public void addOrder(){

    }

    @GetMapping
    public void getOrdersList(){

    }

    @GetMapping("/{orderId}")
    public void getOrderDetail(@PathVariable Long orderId){

    }
}
