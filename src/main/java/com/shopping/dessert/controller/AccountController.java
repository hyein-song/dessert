package com.shopping.dessert.controller;

import com.shopping.dessert.dto.UserDto;
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
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;

    @GetMapping("/register")
    public String getRegisterForm(Model model){
        model.addAttribute("registerForm",new UserDto.Request.RegisterForm());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserDto.Request.RegisterForm registerForm, BindingResult result, Model model){
        if (!registerForm.getPassword().equals(registerForm.getPasswordConfirm())){
            result.rejectValue("passwordConfirm","passwordIncorrect", "비밀번호가 일치하지 않습니다.");
        }

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

    @PostMapping("/loginProc")
    public String loginProc(@Valid UserDto.Request.LoginForm loginForm, BindingResult result, Model model){

        if (result.hasErrors()){
            model.addAttribute("loginForm",loginForm);
            return "user/login";
        }

        userService.login(loginForm);

        return "home";
    }


}
