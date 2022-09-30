package com.shopping.dessert.repository;

import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    Page<OrderEntity> findByUser(UserEntity user, Pageable pageable);

}
