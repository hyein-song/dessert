package com.shopping.dessert.dto;

import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.entity.value.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class PostDto {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostAddForm{

        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        @NotNull
        private PostCategory category;

        private Long productId;

        public static PostEntity toEntity(PostAddForm postAddForm, ProductEntity productEntity, UserEntity user){
            PostCategory category1 = PostCategory.QUESTION;
            if (postAddForm.getCategory().toString().equals("review")){
                category1 = PostCategory.REVIEW;
            }

            return PostEntity
                    .builder()
                    .title(postAddForm.title)
                    .content(postAddForm.getContent())
                    .category(category1)
                    .product(productEntity)
                    .user(user)
                    .build();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PostDetail {

        private String title;
        private String content;
        private PostCategory category;
        private String userName;
//        private Set<ReplyDto> replyDtoSet;

        public static PostDetail of(PostEntity postEntity){
            return builder()
                    .title(postEntity.getTitle())
                    .content(postEntity.getContent())
                    .category(postEntity.getCategory())
                    .userName(postEntity.getUser().getName())
                    .build();

        }

    }

}
