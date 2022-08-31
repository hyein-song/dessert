package com.shopping.dessert.entity;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@DynamicInsert
@DynamicUpdate
public class ProductEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String name;

    private Long price;

    private String content;

    // TODO: Multipart로 image 받기
//    private String image;

    private Long amount;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<CartEntity> cart;

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostEntity> postEntities = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderProductEntity> orderProductEntities = new LinkedHashSet<>();

    public void changProductInfo(ProductDto.Detail detail){
        this.name = detail.getName();
        this.price = detail.getPrice();
        this.content = detail.getContent();
        this.amount = detail.getAmount();
    }

}
