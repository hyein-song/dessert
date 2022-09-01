package com.shopping.dessert.service;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.repository.ProductRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public void updateProduct(ProductDto.Detail detail){
        ProductEntity productEntity = productRepository.findByProductId(detail.getProductId()).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 상품이 존재하지 않습니다.");
        });

        productEntity.changProductInfo(detail);

    }

    @Transactional
    public void deleteProduct(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 상품이 존재하지 않습니다.");
        });

        productRepository.delete(productEntity);

    }

    @Transactional
    public Page<ProductDto.Detail> getProductList(Pageable pageable){
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        return productEntities.map(ProductDto.Detail::of);
    }

    @Transactional
    public ProductDto.Detail getProductDetail(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new IllegalStateException("해당 id를 가진 상품이 존재하지 않습니다.");
        });

        return ProductDto.Detail.of(productEntity);
    }



    public Optional<ProductEntity> getProductByName(String productName){
        return productRepository.findByName(productName);
    }
}
