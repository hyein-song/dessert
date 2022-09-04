package com.shopping.dessert.controller;

import com.shopping.dessert.Custom.CurrentUser;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public String addToCart(@Valid CartDto.Response.CartAddForm cartAddForm, BindingResult result, @CurrentUser UserEntity currentUser, Model model){
        System.out.println(cartAddForm);
        if (currentUser == null){
            return "redirect:/account/login";
        }

        if (result.hasErrors()){
            model.addAttribute("cartAddForm",cartAddForm);
            return "cart/list";
        }

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
