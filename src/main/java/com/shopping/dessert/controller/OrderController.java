package com.shopping.dessert.controller;

import com.shopping.dessert.custom.CurrentUser;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.OrderDto;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.CartService;
import com.shopping.dessert.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/proc")
    public String orderProc(Model model, @CurrentUser UserEntity userEntity){
        List<CartDto.Response.CartDetailForm> cartItems = cartService.getCartlist(userEntity);
        long itemsPrice = cartItems.stream().mapToLong(CartDto.Response.CartDetailForm::getTotalPrice).sum();
        int shippingPrice = itemsPrice > 50000L ? 0 : 3000; // 5만원 이상 무료배송

        OrderDto.OrderProcDto orderProcDto = OrderDto.OrderProcDto
                .builder()
                .itemsPriceSum(itemsPrice)
                .shippingPrice(shippingPrice)
                .totalPrice(itemsPrice+shippingPrice)
                .build();

        model.addAttribute("cartItems",cartItems);
        model.addAttribute("orderProcDto",orderProcDto);
        model.addAttribute("user", UserDto.Response.UserDetailForOrder.of(userEntity));
        return "order/proc";
    }


    @PostMapping("/add")
    public String addOrder(@Valid OrderDto.OrderProcDto orderProcDto, BindingResult result, @CurrentUser UserEntity userEntity, Model model){
        List<CartDto.Response.CartDetailForm> cartItems = cartService.getCartlist(userEntity);

        // 에러시
        if (result.hasErrors()){
            return "redirect:/carts/list";
        }
        // TODO: 결제 방법에 따라 페이지 나뉘기

        Long orderId = orderService.addOrder(orderProcDto, cartItems, userEntity.getUserId());

        //장바구니 비우기
        for(CartDto.Response.CartDetailForm cartItem : cartItems){
            cartService.deleteFromCart(cartItem.getCartId());
        }

        model.addAttribute("orderId",orderId);
        return "order/orderSuccess";
    }

    @GetMapping("/list")
    public String getOrdersList(@CurrentUser UserEntity userEntity, Model model, Pageable pageable){
        model.addAttribute("orderList",orderService.getOrderList(userEntity, pageable));
        return "order/list";
    }

    @GetMapping("/{orderId}")
    public String getOrderProductList(@PathVariable Long orderId, @CurrentUser UserEntity userEntity, Model model){
        OrderDto.OrderDetail order = orderService.getOrderDetail(orderId);
        model.addAttribute("order",order);
        model.addAttribute("user", UserDto.Response.UserDetailForOrder.of(userEntity));
        return "order/detail";
    }
}
