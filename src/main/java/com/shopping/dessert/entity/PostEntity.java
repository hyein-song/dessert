package com.shopping.dessert.entity;

import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.value.PostCategory;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class PostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="userId")
    private UserEntity user;

    @Builder.Default
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ReplyEntity> replyEntities = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="productId")
    private ProductEntity product;

    public void update(PostDto.PostUpdateForm updateForm){
        this.title = updateForm.getTitle();
        this.content = updateForm.getContent();

        if (updateForm.getCategory().equals("review")){
            this.category = PostCategory.REVIEW;
        } else {
            this.category = PostCategory.QUESTION;
        }

    }

}
