package com.shopping.dessert.service;

import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.PostRepository;
import com.shopping.dessert.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    @Transactional
    public void addPost(PostDto.PostAddForm postAddForm, UserEntity user){

        ProductEntity productEntity = productRepository.findByProductId(postAddForm.getProductId()).orElseThrow(()->{
            throw new IllegalStateException("해당 아이디의 제품이 없습니다.");
        });

        PostEntity postEntity = PostDto.PostAddForm.toEntity(postAddForm,productEntity,user);

        postRepository.save(postEntity);
    }
}
