package com.shopping.dessert.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("")
    public String home(Model model){

        // TODO: main에 보여줄 product 추가


        return "home";
    }
}
