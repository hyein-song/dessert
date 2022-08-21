package com.shopping.dessert.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @PostMapping("/join")
    public void join(){

    }

    @GetMapping("/myInfo")
    public void getUserInfo(){

    }

    @PutMapping("/myInfo/update")
    public void updateUserInfo(){

    }

    @DeleteMapping
    public void deleteUser(){

    }

    @GetMapping("/login")
    public void login(){

    }

    @GetMapping("/logout")
    public void Logout(){

    }



}
