package com.shopping.dessert.repository;

import com.shopping.dessert.entity.FileEntity;
import com.shopping.dessert.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {

    List<FileEntity> findByProduct(ProductEntity productEntity);
}
