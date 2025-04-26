package com.onlineshopping.project3.controller;

import com.onlineshopping.project3.dtos.add.RegisterDto;
import com.onlineshopping.project3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;



    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto registerDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.register(registerDto);

            redirectAttributes.addFlashAttribute(
                    "successMessage",
                    "Registration successful! Please log in."
            );

            return "redirect:/auth/login";


        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("registerDto", registerDto);
            return "register";
        }
    }

    @GetMapping("/logout")
    String logout() {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        return "redirect:/";
    }

}
