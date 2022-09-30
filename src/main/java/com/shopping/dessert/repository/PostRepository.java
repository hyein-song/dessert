package com.shopping.dessert.repository;

import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findByUser(UserEntity user,
                                Pageable pageable);
}
