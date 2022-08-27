package com.shopping.dessert.controller;

import com.fasterxml.jackson.core.JsonToken;
import com.shopping.dessert.controller.validation.UserRegisterValidator;
import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.dto.UserDto.Request;
import com.shopping.dessert.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRegisterValidator userRegisterValidator;

    @GetMapping("/register")
    public String registerForm(Model model){
        model.addAttribute("registerForm",new UserDto.Request.RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerForm(@Valid Request.RegisterForm registerForm, BindingResult result, Model model){

        userRegisterValidator.validate(registerForm,result);

        if (result.hasErrors()) {
            model.addAttribute("registerForm",registerForm);
            return "register";
        }

        userService.register(registerForm);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

//
//    @GetMapping("/logout")
//    public String Logout(){
//
//    }
//
//    @GetMapping("/mypage")
//    public String getUserMyPage(){
//
//    }
//
//    @PutMapping("/myInfo/update")
//    public String updateUserMyPage(){
//
//    }
//
//    @DeleteMapping
//    public String deleteUser(){
//
//    }
//




}
