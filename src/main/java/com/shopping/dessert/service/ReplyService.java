package com.shopping.dessert.service;

import com.shopping.dessert.dto.ReplyDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.ReplyEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.exceptionHandler.CustomException;
import com.shopping.dessert.exceptionHandler.ErrorCode;
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
    public void addReply(ReplyDto replyAddForm, UserEntity currentUser){
        UserEntity user = userRepository.findByEmail(currentUser.getEmail()).orElseThrow(()->{
            throw new CustomException("해당 이메일의 유저가 존재하지 않습니다.", ErrorCode.USER_NOT_FOUND);
        });

        PostEntity post = postRepository.findById(replyAddForm.getPostId()).orElseThrow(()->{

            throw new CustomException("해당 id의 post가 존재하지 않습니다.",ErrorCode.POST_NOT_FOUND);
        });

        ReplyEntity replyEntity = ReplyDto.toEntity(replyAddForm,post,user);

        replyRepository.save(replyEntity);

    }

    @Transactional
    public void deleteReply(Long replyId, UserEntity userEntity){
        ReplyEntity reply = replyRepository.findById(replyId).orElseThrow(()->{
            throw new CustomException("해당 id의 reply가 존재하지 않습니다.",ErrorCode.REPLY_NOT_FOUND);
        });

        UserEntity user = userRepository.findById(userEntity.getUserId()).orElseThrow(()->{
            throw new CustomException("해당 id의 유저가 존재하지 않습니다.",ErrorCode.USER_NOT_FOUND);
        });

        if (!reply.getUser().getEmail().equals(user.getEmail())){
            throw new CustomException("해당 글의 작성자가 아닙니다.",ErrorCode.WRITER_NOT_MATCH);
        }

        replyRepository.delete(reply);

    }
}
