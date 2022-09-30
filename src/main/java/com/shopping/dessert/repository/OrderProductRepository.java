package com.shopping.dessert.repository;

import com.shopping.dessert.entity.OrderEntity;
import com.shopping.dessert.entity.OrderProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderProductRepository extends JpaRepository<OrderProductEntity, Long> {

    Set<OrderProductEntity> findByOrder(OrderEntity order);
}
