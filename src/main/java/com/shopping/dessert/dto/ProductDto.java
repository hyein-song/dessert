package com.shopping.dessert.dto;

import com.shopping.dessert.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

        private List<FileDto> fileDtoList;

        @NotNull(message = "상품 수량을 입력하세요.")
        @Range(min=0, max=2000000000, message = "0에서 2,000,000,000 까지만 입력할 수 있습니다.")
        private Long amount;

        private Set<PostDto.PostDetail> reviewSet;
        private Set<PostDto.PostDetail> qnaSet;

        public static ProductDetail of(ProductEntity productEntity){
            return builder()
                    .productId(productEntity.getProductId())
                    .name(productEntity.getName())
                    .price(productEntity.getPrice())
                    .content(productEntity.getContent())
                    .fileDtoList(productEntity.getImages().stream().map(FileDto::of).collect(Collectors.toList()))
                    .amount(productEntity.getAmount())
                    .reviewSet(productEntity.getPostEntities().stream().filter(i->i.getCategory().toString().equalsIgnoreCase("review")).map(PostDto.PostDetail::of).collect(Collectors.toSet()))
                    .qnaSet(productEntity.getPostEntities().stream().filter(i->i.getCategory().toString().equalsIgnoreCase("question")).map(PostDto.PostDetail::of).collect(Collectors.toSet()))
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

            private MultipartFile[] multiParts;

            @NotNull(message = "상품 수량을 입력하세요.")
            @Range(min=0, max=2000000000, message = "0에서 2,000,000,000 까지만 입력할 수 있습니다.")
            private Long amount;

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
