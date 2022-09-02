package com.shopping.dessert.controller;

import com.shopping.dessert.Custom.CurrentUser;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(CartDto.Response.CartAddForm cartAddForm, @CurrentUser UserEntity currentUser){
        cartService.addToCart(cartAddForm, currentUser);
        return "redirect:/carts";
    }

    @GetMapping
    public String getCartList(@CurrentUser UserEntity currentUser, Model model){
        List<CartDto.Response.CartDetailForm> cartItems =  cartService.getCartlist(currentUser);
        model.addAttribute("cartItems",cartItems);
        // 0개 일 시 프론트에서 '장바구니가 비어있습니다.' 출력
        return "cart/list";
    }

//    @PostMapping()
//    public String updateCart(){
//
//    }

//
//    @DeleteMapping("/{cartId}")
//    public String deleteFromCart(@PathVariable Long cartId){
//
//    }
//


}
