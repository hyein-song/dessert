package com.shopping.dessert.service;

import com.shopping.dessert.dto.ReplyDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ReplyEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.repository.PostRepository;
import com.shopping.dessert.repository.ReplyRepository;
import com.shopping.dessert.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public ReplyDto getReply(Long replyId){
        ReplyEntity reply = replyRepository.findById(replyId).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 댓글이 존재하지 않습니다.");
        });

        return ReplyDto.of(reply);
    }

    @Transactional
    public Long addReply(ReplyDto replyAddForm, UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->{
            throw new IllegalStateException("해당 이메일의 유저가 존재하지 않습니다.");
        });

        PostEntity post = postRepository.findById(replyAddForm.getPostId()).orElseThrow(()->{
            throw new IllegalStateException("해당 id의 post가 존재하지 않습니다.");
        });

        ReplyEntity replyEntity = ReplyDto.toEntity(replyAddForm,post,user);

        ReplyEntity savedReply = replyRepository.save(replyEntity);

        return savedReply.getReplyId();
    }

    @Transactional
    public void updateReply(ReplyDto updateForm, UserEntity currentUser){

    }

    @Transactional
    public void deleteReply(Long replyId, UserEntity currentUser){

    }
}
