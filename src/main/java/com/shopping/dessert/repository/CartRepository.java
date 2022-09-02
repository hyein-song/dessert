package com.shopping.dessert.repository;

import com.shopping.dessert.entity.CartEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findByUserAndProduct (UserEntity user, ProductEntity product);
    List<CartEntity> findByUser (UserEntity user);


}
