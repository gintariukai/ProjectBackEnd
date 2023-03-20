package com.app.projectbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home page");
        return "home";
    }

}

