package com.shopping.dessert.dto;

import com.shopping.dessert.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;


public class ProductDto {

    public static class Request{

        @Data
        public static class AddForm {

            @NotBlank(message = "상품명을 입력하세요.")
            private String name;
            @NotBlank(message = "가격을 입력하세요.")
            private Long price;
            @NotBlank(message = "상세 내용을 입력하세요.")
            private String content;
            // TODO: Multipart로 image 받기
//            private String image;
            @NotBlank(message = "상품 수량을 입력하세요.")
            private Long amount;

            // TODO : image file 추가 필요
            public ProductEntity toEntity(){
                return ProductEntity
                        .builder()
                        .name(this.getName())
                        .price(this.getPrice())
                        .content(this.getContent())
                        .amount(this.getAmount())
                        .build();
            }

        }

    }

    public static class Response{

        @Data
        @Builder
        public static class Detail{

            private Long productId;
            private String name;
            private Long price;
            private String content;
            // TODO: Multipart로 image 받기
//            private String image;
            private Long amount;

            public static Detail of(ProductEntity productEntity){
                return builder()
                        .productId(productEntity.getProductId())
                        .name(productEntity.getName())
                        .price(productEntity.getPrice())
                        .content(productEntity.getContent())
                        .amount(productEntity.getAmount())
                        .build();
            }

        }
    }
}
