package com.shopping.dessert.controller;

import com.shopping.dessert.custom.CurrentUser;
import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping("/add")
    public String getPostForm(Model model){
        model.addAttribute("postAddForm", new PostDto.PostAddForm());
        return "post/add";
    }

    @PostMapping("/add")
    public String addPost(@Valid PostDto.PostAddForm postAddForm, BindingResult result, Model model, @CurrentUser UserEntity user, RedirectAttributes re){

        if(result.hasErrors()){
            model.addAttribute("postAddForm", postAddForm);
            return "post/add";
        }
        postService.addPost(postAddForm,user);

        re.addAttribute("productId",postAddForm.getProductId());
        return "redirect:/products/{productId}";
    }
//
//    @GetMapping
//    public String getMyPostsList(){
//
//    }
//
//    @GetMapping("/{postId}")
//    public String getMyPostDetail(@PathVariable Long postId){
//
//    }
//
//    @DeleteMapping("/{postId}")
//    public String deletePost(@PathVariable Long postId){
//
//    }
//
//    @PutMapping("/{postId}")
//    public String updatePost(@PathVariable Long postId){
//
//    }


}
