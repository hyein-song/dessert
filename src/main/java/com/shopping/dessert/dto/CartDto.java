package com.shopping.dessert.dto;

import com.shopping.dessert.entity.CartEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import lombok.Builder;
import lombok.Data;


public class CartDto {

    public static class Response{

        @Data
        public static class CartAddForm{

            Long productId;
            Long amount = 0L;

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
