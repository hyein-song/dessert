package com.shopping.dessert.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    private String title;

    private String content;

    //TODO: enum
    private String category;

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


}
