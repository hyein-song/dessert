package com.shopping.dessert.controller;

import com.shopping.dessert.Custom.CurrentUser;
import com.shopping.dessert.auth.PrincipalDetails;
import com.shopping.dessert.controller.validation.UserRegisterValidator;
import com.shopping.dessert.controller.validation.UserUpdateValidator;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.dto.UserDto.Request;
import com.shopping.dessert.entity.UserEntity;
import com.shopping.dessert.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAccessorOrder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;
    private final UserUpdateValidator userUpdateValidator;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("registerForm",new UserDto.Request.RegisterForm());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerForm(@Valid Request.RegisterForm registerForm, BindingResult result, Model model){
        userRegisterValidator.validate(registerForm,result);
        if (result.hasErrors()) {
            model.addAttribute("registerForm",registerForm);
            return "user/register";
        }

        userService.register(registerForm);
        return "user/registerSuccess";
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("loginForm", new UserDto.Request.LoginForm());
        return "user/login";
    }


    @GetMapping("/mypage")
    public String getUserMyPage(){
        return "user/mypage";
    }

    @GetMapping("/myinfo")
    public String getUserInfo(Model model, @CurrentUser UserEntity userEntity){
        Request.MyInfoUpdateForm myInfoUpdateForm = userService.getMyInfo(userEntity);
        model.addAttribute("myInfoUpdateForm",myInfoUpdateForm);
        return "user/myinfo";
    }

    @PostMapping("/myinfo")
    public String updateUserMyInfo(@Valid Request.MyInfoUpdateForm myInfoUpdateForm, BindingResult result, Model model, @CurrentUser UserEntity userEntity){

        //validation
        userUpdateValidator.validate(myInfoUpdateForm,result);

        //실패 시
        if (result.hasErrors()) {
            model.addAttribute("myInfoUpdateForm",myInfoUpdateForm);
            return "user/myinfo";
        }

        //성공 시
        userService.updateMyInfo(myInfoUpdateForm, userEntity);
        return "redirect:/users/myinfo";
    }


    @GetMapping("/delete")
    public String deleteUser(Model model){
        model.addAttribute("deleteForm", new UserDto.Request.DeleteForm());
        return "user/delete";
    }


    @PostMapping("/delete")
    public String deleteUser(@Valid UserDto.Request.DeleteForm deleteForm, BindingResult result, Model model, @CurrentUser UserEntity userEntity){

        if (result.hasErrors()){
            model.addAttribute("deleteForm",deleteForm);
            return "user/delete";
        }

        try {
            userService.delete(deleteForm,userEntity);
        } catch (Exception e){
            System.out.println(e.getMessage());
            result.reject("deleteFailure", e.getMessage());
            model.addAttribute("deleteForm",deleteForm);
            return "user/delete";
        }

        return "redirect:/users/logout";

    }





}
