package com.shopping.dessert.entity;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    private Long count;

    private Long amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="userId")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="productId")
    private ProductEntity product;


}
