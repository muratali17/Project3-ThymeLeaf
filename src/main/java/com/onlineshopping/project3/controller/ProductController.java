package com.onlineshopping.project3.controller;

import com.onlineshopping.project3.addDTO.ProductAddDTO;
import com.onlineshopping.project3.model.Product;
import com.onlineshopping.project3.model.User;
import com.onlineshopping.project3.repository.ProductRepository;
import com.onlineshopping.project3.service.ProductService;
import com.onlineshopping.project3.updateDTO.ProductUpdateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;

    private final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/all")
    public String allProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "/product/all";
    }

    @GetMapping("/details/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        return "/product/_show";
    }

    @GetMapping("/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new ProductAddDTO());
        return "/product/_add";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") ProductAddDTO product, @RequestParam("img")MultipartFile file) {
        productService.createProduct(product, file);
        return "redirect:/product/all";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, Model model) {
        if(productRepository.findById(id).isEmpty()){
            throw new RuntimeException("Product not found");
        }
        model.addAttribute("product", productService.getProductById(id));
        return "/product/_update";
    }
    @PostMapping("/update")
    public String updateProduct(@ModelAttribute("product") ProductUpdateDTO product, @RequestParam("img")MultipartFile file) {
        productService.updateProduct(product, file);
        return "redirect:/product/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model) {
        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");
        model.addAttribute("product", productRepository.findById(id).orElse(null));
        return "product/_delete";
    }
    @PostMapping("/delete")
    public String deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/product/all";
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
