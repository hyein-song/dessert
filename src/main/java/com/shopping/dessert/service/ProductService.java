package com.shopping.dessert.service;

import com.shopping.dessert.dto.ProductDto;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
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
    private final FileService fileService;

    @Transactional
    public Long addProduct(ProductDto.ProductAddForm productAddForm) {

        ProductEntity productEntity = productAddForm.toEntity();
        ProductEntity savedProduct = productRepository.save(productEntity);

        fileService.uploadFile(productAddForm.getMultiParts(), savedProduct);

        return savedProduct.getProductId();
    }

    @Transactional
    public void updateProduct(ProductDto.ProductDetail productDetail){
        ProductEntity productEntity = productRepository.findByProductId(productDetail.getProductId()).orElseThrow(()->{
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        productEntity.changProductInfo(productDetail);

    }

    @Transactional
    public void deleteProduct(Long productId){
        ProductEntity productEntity = productRepository.findByProductId(productId).orElseThrow(()->{
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        fileService.deleteFile(productEntity);
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
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        return ProductDto.ProductDetail.of(productEntity);
    }

    public Optional<ProductEntity> getProductByName(String productName){
        return productRepository.findByName(productName);
    }
}
