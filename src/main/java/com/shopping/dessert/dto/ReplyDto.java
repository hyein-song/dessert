package com.shopping.dessert.dto;

import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ReplyEntity;
import com.shopping.dessert.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReplyDto {

        @NotBlank(message = "내용을 입력해주세요")
        private String content;

        @NotNull
        private Long postId;

        private Long userId;
        private LocalDateTime localDateTime;

        public static ReplyEntity toEntity(ReplyDto replyDto, PostEntity post, UserEntity userEntity){
            return ReplyEntity
                    .builder()
                    .content(replyDto.getContent())
                    .post(post)
                    .user(userEntity)
                    .build();
        }

    public static ReplyDto of(ReplyEntity replyEntity){
        return ReplyDto
                .builder()
                .content(replyEntity.getContent())
                .postId(replyEntity.getPost().getPostId())
                .userId(replyEntity.getUser().getUserId())
                .localDateTime(replyEntity.getCreatedDateTime()!=null ? replyEntity.getCreatedDateTime() : replyEntity.getModifiedDateTime())
                .build();

    }

}
