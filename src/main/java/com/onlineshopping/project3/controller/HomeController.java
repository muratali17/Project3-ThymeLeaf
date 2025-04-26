package com.onlineshopping.project3.controller;

import com.onlineshopping.project3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }



    // Buraya koydum cunku default route burasi
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

}
