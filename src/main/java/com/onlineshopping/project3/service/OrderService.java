package com.onlineshopping.project3.service;

import com.onlineshopping.project3.addDTO.OrderAddDTO;
import com.onlineshopping.project3.exception.ResourceNotFoundException;
import com.onlineshopping.project3.getDTO.OrderGetDTO;
import com.onlineshopping.project3.model.Order;
import com.onlineshopping.project3.model.Product;
import com.onlineshopping.project3.model.User;
import com.onlineshopping.project3.repository.OrderRepository;
import com.onlineshopping.project3.repository.ProductRepository;
import com.onlineshopping.project3.repository.UserRepository;
import com.onlineshopping.project3.updateDTO.OrderUpdateDTO;
import org.springframework.stereotype.Service;
import com.onlineshopping.project3.exception.ErrorMessages;

import java.util.List;

@Service
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(UserRepository userRepository, ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public OrderGetDTO getOrderById(Long id) {
        Order order =  orderRepository.findById(id)
                .orElse(null);
        if(order != null)
            return order.toOrderGetDTO();
        else
            return null;

    }
    public OrderGetDTO getOrderByIdFull(Long id) {
        Order order =  orderRepository.findById(id)
                .orElse(null);
        if(order != null)
            return order.viewAsOrderDTOAll();
        else
            return null;

    }

    public OrderUpdateDTO getOrderByIdUpdateDTO(Long id) {
        Order order =  orderRepository.findById(id)
                .orElse(null);
        if(order != null)
            return order.toOrderUpdateDTO();
        else
            return null;
    }

    public List<OrderGetDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(Order::viewAsOrderWithCustomerAndProducts).toList();
    }

    public OrderGetDTO createOrder(OrderAddDTO OrderAddDTO) {

        User customer = userRepository.findById(OrderAddDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ":" + OrderAddDTO.getCustomerId()));

        Product product = productRepository.findById(OrderAddDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND + ":" + OrderAddDTO.getProductId()));

        Order order =
                new Order(OrderAddDTO.getDate(),OrderAddDTO.getCity(),OrderAddDTO.getStatus(),customer,product);

        return orderRepository.save(order).viewAsOrderDTOAll();
    }

    public OrderGetDTO updateOrder(OrderUpdateDTO orderUpdateDTO) {

        Long id = orderUpdateDTO.getId();

        if(id == null || id <= 0)
            throw new IllegalArgumentException("id is invalid");

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ORDER_NOT_FOUND + ":" + id));

        User customer = userRepository.findById(orderUpdateDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.USER_NOT_FOUND + ":" + orderUpdateDTO.getCustomerId()));

        Product product = productRepository.findById(orderUpdateDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.PRODUCT_NOT_FOUND + ":" + orderUpdateDTO.getProductId()));

        Order orderToUpdate = new Order(id,orderUpdateDTO.getDate(),orderUpdateDTO.getCity(),orderUpdateDTO.getStatus(),customer,product);

        return orderRepository.save(orderToUpdate).viewAsOrderDTOAll();
    }


    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ErrorMessages.ORDER_NOT_FOUND + ":" + id));

        orderRepository.delete(order);
    }

}
