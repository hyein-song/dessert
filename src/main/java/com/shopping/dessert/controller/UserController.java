package com.shopping.dessert.controller;

import com.shopping.dessert.dto.UserDto;
import com.shopping.dessert.dto.UserDto.Request;
import com.shopping.dessert.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    final private UserService userService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("registerForm",new UserDto.Request.Register());
        return "register";
    }
    @PostMapping("/register")
    public String register(Request.Register registerForm){
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
//    @GetMapping("/myInfo")
//    public String getUserInfo(){
//
//    }
//
//    @PutMapping("/myInfo/update")
//    public String updateUserInfo(){
//
//    }
//
//    @DeleteMapping
//    public String deleteUser(){
//
//    }
//




}
