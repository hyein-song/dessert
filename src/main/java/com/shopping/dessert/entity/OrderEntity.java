package com.shopping.dessert.entity;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
public class OrderEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    private Long itemsPriceSum;
    private Integer shippingPrice;
    private Long totalPrice;
    String payment;

    private Long amount;

//    private String orderAddress;
//
    private String orderState;
//
//    //TODO: ordernum 생성 알고리즘 필요
//    private String orderNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name="userId")
    private UserEntity user;

    @Builder.Default
    @OneToMany(mappedBy = "order")
    private Set<OrderProductEntity> orderProductEntities = new LinkedHashSet<>();

}
