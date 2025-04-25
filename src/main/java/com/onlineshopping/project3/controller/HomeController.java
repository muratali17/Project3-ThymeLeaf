package com.onlineshopping.project3.controller;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
@RequestMapping
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        String role = authorities.iterator().next().getAuthority().substring(5);
        model.addAttribute("role", role);
        model.addAttribute("principal", SecurityContextHolder.getContext().getAuthentication().getName());
        return "login";
    }

    @GetMapping("/logout")
    String logout() {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        return "redirect:/";
    }
}
