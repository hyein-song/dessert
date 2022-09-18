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

    @GetMapping("/{replyId}")
    public String getReply(@PathVariable Long replyId, Model model){
        ReplyDto replyDto = replyService.getReply(replyId);
        model.addAttribute("replyDto",replyDto);
        return "reply/detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/add")
    public String getAddReplyForm(@RequestParam Long postId, Model model){
        ReplyDto replyDto = ReplyDto
                .builder()
                .postId(postId)
                .build();

        model.addAttribute("replyDto",replyDto);
        return "reply/add";

    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add")
    public String addReply(@Valid ReplyDto replyDto, BindingResult result, Model model, RedirectAttributes re, @CurrentUser UserEntity user){
        if (result.hasErrors()){
            model.addAttribute("replyAddForm",replyDto);
            return "reply/add";
        }

        Long replyId = replyService.addReply(replyDto,user);

        re.addAttribute("replyId",replyId);
        return "redirect:/replies/{replyId}";

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{replyId}")
    public String getUpdateReplyForm(@PathVariable Long replyId, Model model){

        return "redirect:/post/detail/{postId}";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{replyId}")
    public String updateReply(@PathVariable Long replyId, @CurrentUser UserEntity user){

        return "redirect:/post/detail/{postId}";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{replyId}")
    public String deleteReply(@PathVariable Long replyId, UserEntity user){

        return "redirect:/post/detail/{postId}";
    }



}
