package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    @PostMapping
    public void addReply(){

    }

    @GetMapping
    public void getReply(){

    }

    @DeleteMapping("/{replyId}")
    public void deleteReply(@PathVariable Long replyId){

    }

    @PutMapping("/{replyId}")
    public void updateReply(@PathVariable Long replyId){

    }

}
