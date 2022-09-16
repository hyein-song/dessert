package com.shopping.dessert.service;

import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ProductEntity;
import com.shopping.dessert.entity.UserEntity;
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
            throw new IllegalStateException("해당 아이디의 제품이 없습니다.");
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
            throw new IllegalStateException("해당 id의 post가 존재하지 않습니다.");
        });

        return PostDto.PostDetail.of(post);

    }


    public void updatePost(PostDto.PostUpdateForm postUpdateForm, UserEntity user){
        PostEntity post = postRepository.findById(postUpdateForm.getPostId()).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 post가 존재하지 않습니다.");
        });

        if (!post.getUser().getEmail().equals(user.getEmail())){
            throw new IllegalStateException("작성자가 아닙니다.");
        }

        ProductEntity productEntity = productRepository.findByProductId(postUpdateForm.getProductId()).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 product가 존재하지 않습니다.");
        });



        post.update(postUpdateForm, productEntity);

    }

    public void deletePost(Long postId, UserEntity user){
        PostEntity post = postRepository.findById(postId).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 post가 존재하지 않습니다.");
        });

        if (!post.getUser().getEmail().equals(user.getEmail())){
            throw new IllegalStateException("작성자가 아닙니다.");
        }

        postRepository.delete(post);

    }
}
