package com.shopping.dessert.dto;

import com.shopping.dessert.entity.CartEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class CartDto {

    public static class Response{

        @Data
        public static class CartAddForm{

            @NotNull
            Long productId;

            @NotNull
            @Min(1)
            Long amount = 1L;

        }

        @Builder
        @Data
        public static class CartDetailForm{
            private Long cartId;
            private Long amount;
            private Long totalPrice;
            private ProductDto.ProductDetail productDetail;

            public static CartDetailForm of(CartEntity cart){
                return builder()
                        .cartId(cart.getCartId())
                        .amount(cart.getAmount())
                        .totalPrice(cart.getAmount()*(cart.getProduct().getPrice()))
                        .productDetail(ProductDto.ProductDetail.of(cart.getProduct()))
                        .build();
            }
        }

    }
}
