package com.shopping.dessert.service;

import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
import com.shopping.dessert.repository.PostRepository;
import com.shopping.dessert.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ProductRepository productRepository;
    @Transactional
    public Long addPost(PostDto.PostAddForm postAddForm, UserEntity user){

        ProductEntity productEntity = productRepository.findByProductId(postAddForm.getProductId()).orElseThrow(()->{
            throw new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        });

        PostEntity postEntity = PostDto.PostAddForm.toEntity(postAddForm,productEntity,user);

        PostEntity savedPost = postRepository.save(postEntity);

        return savedPost.getPostId();
    }

    @Transactional
    public Page<PostDto.PostDetail> getMyPostList(UserEntity user, Pageable pageable){
        Page<PostEntity> postEntities = postRepository.findByUser(user,pageable);
        return postEntities.map(PostDto.PostDetail::of);
    }

    @Transactional
    public PostDto.PostDetail getPostDetail(Long postId){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        });

        return PostDto.PostDetail.of(post);

    }

    @Transactional
    public void updatePost(PostDto.PostUpdateForm postUpdateForm, UserEntity user){
        PostEntity post = postRepository.findById(postUpdateForm.getPostId()).orElseThrow(()->{
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        });

        if (!post.getUser().getEmail().equals(user.getEmail())){
            throw new CustomException(ErrorCode.WRITER_NOT_MATCH);
        }

        post.update(postUpdateForm);

    }

    @Transactional
    public void deletePost(Long postId, UserEntity user){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw new CustomException(ErrorCode.POST_NOT_FOUND);
        });

        if (!post.getUser().getEmail().equals(user.getEmail())){
            throw new CustomException(ErrorCode.WRITER_NOT_MATCH);
        }

        postRepository.delete(post);

    }
}
