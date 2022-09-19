package com.shopping.dessert.controller;

import com.shopping.dessert.custom.CurrentUser;
import com.shopping.dessert.dto.ReplyDto;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/replies")
public class ReplyController {

    private final ReplyService replyService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public String addReply(@Valid ReplyDto replyDto, BindingResult result, Model model, RedirectAttributes re, @CurrentUser UserEntity user){
        if (result.hasErrors()){
            model.addAttribute("replyDto",replyDto);
            return "reply/add";
        }

        replyService.addReply(replyDto,user);

        re.addAttribute("postId",replyDto.getPostId());
        return "redirect:/posts/{postId}";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{replyId}")
    public String deleteReply(ReplyDto replyDto, RedirectAttributes re){
        replyService.deleteReply(replyDto.getReplyId());
        re.addAttribute("postId",replyDto.getPostId());
        return "redirect:/posts/{postId}";
    }

}
