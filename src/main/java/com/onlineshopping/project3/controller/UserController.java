package com.onlineshopping.project3.controller;

import com.onlineshopping.project3.dtos.add.UserAddDTO;
import com.onlineshopping.project3.service.UserService;
import com.onlineshopping.project3.dtos.updateDTO.UserUpdateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "user/all";
    }

    @GetMapping("/details/{id}")
    public String getUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "user/_show";
    }

    @GetMapping("/add")
    public String addUser(Model model) {
        model.addAttribute("user", new UserAddDTO());
        return "user/_add";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("user") UserAddDTO userAddDTO, @RequestParam("img")MultipartFile image) {

        userService.createUser(userAddDTO, image);

        return "redirect:/user/all";
    }


    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, Model model) {
        if(userService.getUserById(id) == null) {
            throw new RuntimeException("User not found");
        }
        model.addAttribute("user", userService.getUserById(id));
        return "user/_update";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") UserUpdateDTO user, @RequestParam("img")MultipartFile image) {

        userService.updateUser(user, image);
        return "redirect:/user/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, Model model) {
        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");
        model.addAttribute("user", userService.getUserById(id));
        return "user/_delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id) {

        userService.deleteUser(id);

        return "redirect:/user/all";
    }

    @GetMapping(value="/images/{imageUrl}")
    @ResponseBody
    public byte[] getImage(@PathVariable(value="imageUrl") String imageUrl) throws IOException {

        String uploadDirMain = "src/main/resources/static/images/" + imageUrl;
        Path uploadPathMain = Paths.get(uploadDirMain);
        File file = new File(uploadPathMain.toString());

        return Files.readAllBytes(file.toPath());
    }
}
