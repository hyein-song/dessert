package com.shopping.dessert.controller;

import com.shopping.dessert.custom.CurrentUser;
import com.shopping.dessert.dto.CartDto;
import com.shopping.dessert.dto.OrderDto;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/proc")
    public String orderProc(List<CartDto.Response.CartDetailForm> cartItems, Model model, @CurrentUser UserEntity userEntity){

        long itemsPrice = cartItems.stream().mapToLong(CartDto.Response.CartDetailForm::getTotalPrice).sum();
        int shippingPrice = itemsPrice > 50000L ? 0 : 3000; // 5만원 이상 무료배송

        OrderDto.OrderProcDto orderProcDto = OrderDto.OrderProcDto
                .builder()
                .itemsPriceSum(itemsPrice)
                .shippingPrice(shippingPrice)
                .totalPrice(itemsPrice+shippingPrice)
                .cartItems(cartItems)
                .build();

        model.addAttribute("orderProcDto",orderProcDto);
        model.addAttribute("user", UserDto.Response.UserDetailForOrder.of(userEntity));
        return "order/proc";
    }


    @PostMapping("/add")
    public String addOrder(@RequestBody OrderDto.OrderProcDto orderProcDto, BindingResult result, @CurrentUser UserEntity userEntity){
        // 에러시
        if (result.hasErrors()){
            return "carts/list";
        }

        orderService.addOrder(orderProcDto, userEntity.getUserId());
        return "order/orderSuccess";
    }

    @GetMapping("/list")
    public String getOrdersList(@CurrentUser UserEntity userEntity, Model model){
        // user 세션 값 가져오는게 아니고 조회해서 가져와야될듯??
        System.out.println(userEntity.getOrderEntities());
        model.addAttribute("orderList",userEntity.getOrderEntities().stream().map(OrderDto.OrderDetail::of).collect(Collectors.toList()));
        return "order/list";
    }

    @GetMapping("/{orderId}")
    public String getOrderProductList(@PathVariable Long orderId, Model model){
        Set<OrderDto.OrderProductDetail> orderProductList = orderService.getOrderProductList(orderId);
        model.addAttribute("orderProductList",orderProductList);
        return "order/detail";
    }
}
