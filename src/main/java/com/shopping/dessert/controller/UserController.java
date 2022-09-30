package com.shopping.dessert.controller;

import com.shopping.dessert.annotation.CurrentUser;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.dto.UserDto.Request;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;


    @GetMapping("/mypage")
    public String getUserMyPage(){
        return "user/mypage";
    }

    @GetMapping("/myinfo")
    public String getUserInfo(Model model, @CurrentUser UserEntity userEntity){
        model.addAttribute("myInfoUpdateForm", Request.MyInfoUpdateForm.of(userEntity));
        return "user/myinfo";
    }

    @PostMapping("/myinfo")
    public String updateUserMyInfo(@Valid Request.MyInfoUpdateForm myInfoUpdateForm, BindingResult result, Model model){

        //validation
        if (!myInfoUpdateForm.getPassword().equals(myInfoUpdateForm.getPasswordConfirm())){
            result.rejectValue("passwordConfirm","passwordIncorrect", "비밀번호가 일치하지 않습니다.");
        }

        //실패 시
        if (result.hasErrors()) {
            model.addAttribute("myInfoUpdateForm",myInfoUpdateForm);
            return "user/myinfo";
        }

        //성공 시
        userService.updateMyInfo(myInfoUpdateForm);
        return "redirect:/users/myinfo";
    }


    @GetMapping("/delete")
    public String getDeleteUserForm(Model model){
        model.addAttribute("userDeleteForm", new Request.UserDeleteForm());
        return "user/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@Valid UserDto.Request.UserDeleteForm userDeleteForm, BindingResult result, Model model, @CurrentUser UserEntity userEntity){

        if (result.hasErrors()){
            model.addAttribute("deleteForm", userDeleteForm);
            return "user/delete";
        }

        try {
            userService.delete(userDeleteForm);
        } catch (Exception e){
            System.out.println(e.getMessage());
            result.reject("deleteFailure", e.getMessage());
            model.addAttribute("deleteForm", userDeleteForm);
            return "user/delete";
        }

        return "redirect:/users/logout";

    }



}
