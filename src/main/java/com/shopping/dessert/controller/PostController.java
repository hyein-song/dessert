package com.shopping.dessert.controller;

import com.shopping.dessert.custom.CurrentUser;
import com.shopping.dessert.dto.PostDto;
import com.shopping.dessert.entity.PostEntity;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/add")
    public String getPostForm(Model model){
        model.addAttribute("postAddForm", new PostDto.PostAddForm());
        return "post/add";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
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
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/myPostList")
    public String getMyPostsList(@CurrentUser UserEntity user, Model model, Pageable pageable){

        Page<PostDto.PostDetail> postDetailPage = postService.getMyPostList(user, pageable);
        model.addAttribute("postDetailPage",postDetailPage);

        return "post/myPostList";
    }

    @GetMapping("/{postId}")
    public String getMyPostDetail(@PathVariable Long postId, Model model){
        PostDto.PostDetail postDetail = postService.getPostDetail(postId);
        model.addAttribute("postDetail",postDetail);
        return "post/detail";
    }

//    @PostMapping("/delete/{postId}")
//    public String deletePost(@PathVariable Long postId){
//
//        return "";
//    }
//
//    @PostMapping("/update/{postId}")
//    public String updatePost(@PathVariable Long postId,  RedirectAttributes re){
//
//        postService.updatePost(postId);
//
//        re.addAttribute("postId",postId);
//        return "redirect:/posts/{postId}";
//    }


}
