package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    @PostMapping
    public void addPost(){

    }

    @GetMapping
    public void getMyPostsList(){

    }

    @GetMapping("/{postId}")
    public void getMyPostDetail(@PathVariable Long postId){

    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){

    }

    @PutMapping("/{postId}")
    public void updatePost(@PathVariable Long postId){

    }


}
