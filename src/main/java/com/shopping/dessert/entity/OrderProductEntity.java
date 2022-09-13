package com.shopping.dessert.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class OrderProductEntity extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    //개수
    private Long amount;

    //상품 개수 * 개당 가격
    private Long totalPrice;

    @ManyToOne
    @NotNull
    @JoinColumn(name= "orderId")
    private OrderEntity order;

    @ManyToOne
    @NotNull
    @JoinColumn(name="productId")
    private ProductEntity product;
}
