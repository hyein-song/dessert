package com.shopping.dessert.service;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

//    @Transactional
//    public ProductDto.Response.ProductDetail addProduct(ProductDto.Request.ProductAdd productAddRequest) {
//
//        // TODO : image file 추가 필요
//        ProductEntity productEntity = ProductEntity
//                .builder()
//                .name(productAddRequest.getName())
//                .price(productAddRequest.getPrice())
//                .content(productAddRequest.getContent())
//                .count(productAddRequest.getCount())
//                .productRegTimeDate(LocalDateTime.now())
//                .build();
//
//        ProductEntity savedProductEntity = productRepository.save(productEntity);
//
//        return ProductDto.Response.ProductDetail.convertToDto(savedProductEntity);
//
//    }

    @Transactional
    public void addProduct(ProductDto.Request.Add productAddRequest) {

        // TODO : image file 추가 필요
        ProductEntity productEntity = ProductEntity
                .builder()
                .name(productAddRequest.getName())
                .price(productAddRequest.getPrice())
                .content(productAddRequest.getContent())
                .count(productAddRequest.getCount())
                .build();

        productRepository.save(productEntity);

    }
}
