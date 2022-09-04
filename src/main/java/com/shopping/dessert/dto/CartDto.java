package com.shopping.dessert.dto;

import com.shopping.dessert.entity.CartEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
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

            public static CartDetailForm of(CartEntity cart){
                return builder()
                        .cartId(cart.getCartId())
                        .amount(cart.getAmount())
                        .build();
            }
        }

    }
}
