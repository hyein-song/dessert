package com.shopping.dessert.dto;

import com.shopping.dessert.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;


public class ProductDto {

    public static class Request{

        @Data
        public static class Add {

            private String name;
            private Long price;
            private String content;
            // TODO: Multipart로 image 받기
//            private String image;
            private Long count;


        }

    }

    public static class Response{

        @Data
        @Builder
        public static class Detail{

            private String name;
            private Long price;
            private String content;
            // TODO: Multipart로 image 받기
//            private String image;
            private Long count;

            public static Detail of(ProductEntity productEntity){
                return builder()
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .content(productEntity.getContent())
                        .count(productEntity.getCount())
                        .build();
            }

        }
    }
}
