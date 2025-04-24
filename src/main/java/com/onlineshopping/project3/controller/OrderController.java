package com.onlineshopping.project3.controller;

import com.onlineshopping.project3.addDTO.OrderAddDTO;
import com.onlineshopping.project3.getDTO.OrderGetDTO;
import com.onlineshopping.project3.model.Order;
import com.onlineshopping.project3.repository.OrderRepository;
import com.onlineshopping.project3.repository.ProductRepository;
import com.onlineshopping.project3.repository.UserRepository;
import com.onlineshopping.project3.service.OrderService;
import com.onlineshopping.project3.service.ProductService;
import com.onlineshopping.project3.service.UserService;
import com.onlineshopping.project3.updateDTO.OrderUpdateDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/order")
public class OrderController {

    private final OrderRepository orderRepository;

    private final ProductRepository productRepository;

    private final UserRepository userRepository;

    private final OrderService orderService;

    private final ProductService productService;

    private final UserService userService;

    public OrderController(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository, OrderService orderService, ProductService productService, UserService userService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/all")
    public String allOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "/order/all";
    }

    @GetMapping("/add")
    public String addOrder(Model model) {
        model.addAttribute("order", new OrderAddDTO());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("customers", userService.getAllUsers());

        return "/order/_add";
    }

    @PostMapping("/add")
    public String addOrder(@ModelAttribute("order") OrderAddDTO order) {
        orderService.createOrder(order);
        return "redirect:/order/all";
    }

    @GetMapping("/details/{id}")
    public String getOrder(@PathVariable("id") Long id, Model model) {
        model.addAttribute("order",orderService.getOrderByIdFull(id));
        return "/order/_show";
    }

    @GetMapping("/update/{id}")
    public String updateOrder(@PathVariable("id") Long id, Model model) {
        if(orderService.getOrderById(id) == null) {
            throw new RuntimeException("Order not found");
        }
        OrderGetDTO orderGetDTO = orderService.getOrderByIdFull(id);

        model.addAttribute("order",orderGetDTO.toOrderUpdateDTO());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("customers", userService.getAllUsers());
        model.addAttribute("customerName", orderGetDTO.getCustomer().getName());
        model.addAttribute("productName", orderGetDTO.getProduct().getName());
        return "/order/_update";
    }

    @PostMapping("/update")
    public String updateOrder(@ModelAttribute("order") OrderUpdateDTO order) {
        orderService.updateOrder(order);
        return "redirect:/order/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Long id, Model model) {
        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");
        model.addAttribute("order", orderService.getOrderById(id));
        return "order/_delete";
    }
    @PostMapping("/delete")
    public String deleteOrder(@RequestParam("id") Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order/all";
    }
}
