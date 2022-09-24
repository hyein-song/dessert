package com.shopping.dessert.entity;

import com.shopping.dessert.dto.ProductDto;
import com.sun.xml.bind.v2.TODO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FileEntity> images = new ArrayList<>();

    private Long amount;


    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<CartEntity> cart;

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PostEntity> postEntities = new LinkedHashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderProductEntity> orderProductEntities = new LinkedHashSet<>();

    public void changProductInfo(ProductDto.ProductDetail productDetail){
        this.name = productDetail.getName();
        this.price = productDetail.getPrice();
        this.content = productDetail.getContent();
        this.amount = productDetail.getAmount();
    }

}
