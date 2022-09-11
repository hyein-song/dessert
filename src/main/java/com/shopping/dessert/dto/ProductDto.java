package com.shopping.dessert.dto;

import com.shopping.dessert.entity.ProductEntity;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class ProductDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ProductDetail {

        private Long productId;

        @Column(unique = true)
        @NotBlank(message = "상품명을 입력하세요.")
        private String name;

        @NotNull(message = "가격을 입력하세요.")
        @Min(0)
        private Long price;

        @NotBlank(message = "상세 내용을 입력하세요.")
        private String content;

        // TODO: Multipart로 image 받기
//            private String image;

        @NotNull(message = "상품 수량을 입력하세요.")
        @Range(min=0, max=2000000000, message = "0에서 2,000,000,000 까지만 입력할 수 있습니다.")
        private Long amount;

        public static ProductDetail of(ProductEntity productEntity){
            return builder()
                    .productId(productEntity.getProductId())
                    .name(productEntity.getName())
                    .price(productEntity.getPrice())
                    .content(productEntity.getContent())
                    .amount(productEntity.getAmount())
                    .build();
        }

        public ProductEntity toEntity(){
            return ProductEntity
                    .builder()
                    .productId(this.getProductId())
                    .name(this.getName())
                    .price(this.getPrice())
                    .content(this.getContent())
                    .amount(this.getAmount())
                    .build();
        }

    }

    public static class Request{

        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ProductAddForm {

            @NotBlank(message = "상품명을 입력하세요.")
            private String name;

            @NotNull(message = "가격을 입력하세요.")
            @Min(0)
            private Long price;

            @NotBlank(message = "상세 내용을 입력하세요.")
            private String content;

            // TODO: Multipart로 image 받기
//            private String image;

            @NotNull(message = "상품 수량을 입력하세요.")
            @Range(min=0, max=2000000000, message = "0에서 2,000,000,000 까지만 입력할 수 있습니다.")
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


    }
}
