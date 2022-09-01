package com.shopping.dessert.repository;

import com.shopping.dessert.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findByProductId (Long productId);
    Optional<ProductEntity> findByName (String name);
    Page<ProductEntity> findAll (Pageable pageable);

}
