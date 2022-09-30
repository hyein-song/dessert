package com.shopping.dessert.dto;

import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class OrderDto {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProcDto{

        @NotNull
        private Long itemsPriceSum;
        @NotNull
        private Integer shippingPrice;
        @NotNull
        private Long totalPrice;
        @NotBlank
        private String payment;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderListForm{

        private Long orderId;
        private Long price;
        private Long amount;
        private String orderState;

        public static OrderListForm of(OrderEntity order){
            return builder()
                    .orderId(order.getOrderId())
                    .price(order.getTotalPrice())
                    .amount(order.getAmount())
                    .orderState(order.getOrderState())
                    .build();
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderDetail{

        private Long orderId;

        private Long itemsPriceSum;
        private Integer shippingPrice;
        private Long totalPrice;
        private String payment;

        private Long amount;
        private String orderState;
        private Set<OrderProductDetail> orderProductDetailSet;

        public static OrderDetail of(OrderEntity order, Set<OrderProductDetail> orderProductDetails){
            return builder()
                    .orderId(order.getOrderId())
                    .itemsPriceSum(order.getItemsPriceSum())
                    .shippingPrice(order.getShippingPrice())
                    .totalPrice(order.getTotalPrice())
                    .payment(order.getPayment())
                    .amount(order.getAmount())
                    .orderState(order.getOrderState())
                    .orderProductDetailSet(orderProductDetails)
                    .build();
        }

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderProductDetail{

        private Long amount;
        private Long totalPrice;
        private ProductDto.ProductDetail productDetail;

        public static OrderProductDetail of(OrderProductEntity orderProductEntity){
            return builder()
                    .amount(orderProductEntity.getAmount())
                    .totalPrice(orderProductEntity.getTotalPrice())
                    .productDetail(ProductDto.ProductDetail.of(orderProductEntity.getProduct()))
                    .build();
        }
    }

}
