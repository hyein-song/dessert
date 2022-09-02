package com.shopping.dessert.service;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long addProduct(ProductDto.Request.ProductAddForm productAddForm) {

        ProductEntity productEntity = productAddForm.toEntity();

        ProductEntity product = productRepository.save(productEntity);
        return product.getProductId();
    }

    @Transactional
    public void updateProduct(ProductDto.ProductDetail productDetail){
        ProductEntity productEntity = productRepository.findByProductId(productDetail.getProductId()).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 상품이 존재하지 않습니다.");
        });

        productEntity.changProductInfo(productDetail);

    }

    @Transactional
    public void deleteProduct(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 상품이 존재하지 않습니다.");
        });

        productRepository.delete(productEntity);

    }

    @Transactional
    public Page<ProductDto.ProductDetail> getProductList(Pageable pageable){
        Page<ProductEntity> productEntities = productRepository.findAll(pageable);
        return productEntities.map(ProductDto.ProductDetail::of);
    }

    @Transactional
    public ProductDto.ProductDetail getProductDetail(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new IllegalStateException("해당 id를 가진 상품이 존재하지 않습니다.");
        });

        return ProductDto.ProductDetail.of(productEntity);
    }



    public Optional<ProductEntity> getProductByName(String productName){
        return productRepository.findByName(productName);
    }
}
