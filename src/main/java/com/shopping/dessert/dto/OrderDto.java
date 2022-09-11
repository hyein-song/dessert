package com.shopping.dessert.dto;

import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class OrderDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProcDto{

        long itemsPriceSum;
        int shippingPrice;
        long totalPrice;
        List<CartDto.Response.CartDetailForm> cartItems;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetail{

        private Long orderId;
        private Long price;
        private Long count;
        private String orderState;

        public static OrderDetail of(OrderEntity order){
            return builder()
                    .orderId(order.getOrderId())
                    .price(order.getPrice())
                    .count(order.getCount())
                    .orderState(order.getOrderState())
                    .build();
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDetail{

        OrderDetail orderDetail;
        ProductDto.ProductDetail productDetail;

        public static OrderProductDetail of(OrderProductEntity orderProductEntity){
            return builder()
                    .orderDetail(OrderDetail.of(orderProductEntity.getOrder()))
                    .productDetail(ProductDto.ProductDetail.of(orderProductEntity.getProduct()))
                    .build();
        }
    }

}
