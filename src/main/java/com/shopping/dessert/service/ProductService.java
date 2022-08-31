package com.shopping.dessert.service;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long addProduct(ProductDto.Request.AddForm addForm) {

        ProductEntity productEntity = addForm.toEntity();

        ProductEntity product = productRepository.save(productEntity);
        return product.getProductId();
    }

    @Transactional
    public List<ProductDto.Response.Detail> getProductList(){

        List<ProductEntity> productEntities = productRepository.findAll();

        return productEntities.stream()
                .map(ProductDto.Response.Detail::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDto.Response.Detail getProductDetail(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new IllegalStateException("해당 id를 가진 상품이 존재하지 않습니다.");
        });

        return ProductDto.Response.Detail.of(productEntity);
    }


    public boolean isExistedName(String name){
        return productRepository.existsByName(name);
    }
}
