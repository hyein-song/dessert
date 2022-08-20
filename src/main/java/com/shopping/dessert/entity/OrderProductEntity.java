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
public class OrderProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    @ManyToOne
    @NotNull
    @JoinColumn(name= "orderId")
    private OrderEntity order;

    @ManyToOne
    @NotNull
    @JoinColumn(name="productId")
    private ProductEntity product;
}
