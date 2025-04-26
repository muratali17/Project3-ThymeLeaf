package com.onlineshopping.project3.model;

import com.onlineshopping.project3.enums.Status;
import com.onlineshopping.project3.dtos.get.OrderGetDTO;
import com.onlineshopping.project3.dtos.get.ProductGetDTO;
import com.onlineshopping.project3.dtos.get.UserGetDTO;
import com.onlineshopping.project3.dtos.updateDTO.OrderUpdateDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name="city", nullable = false, length = 32)
    private String city;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public OrderGetDTO viewAsOrderDTOAll() {
        UserGetDTO userGetDTO = customer.toUserGetDTO();

        ProductGetDTO productGetDTO = product.toProductGetDTO();

        return new OrderGetDTO(id,date,city,status,userGetDTO,productGetDTO);
    }

    public OrderGetDTO toOrderGetDTO() {
        return new OrderGetDTO(id,date,city,status);
    }

    public OrderGetDTO viewAsOrderWithCustomerAndProducts() {
        UserGetDTO userGetDTO = new UserGetDTO(
                customer.getId(),
                customer.getName(),
                customer.getAddress(),
                customer.getPhone(),
                customer.getUsername(),
                customer.getPassword(),
                customer.getRole(),
                customer.getImageUrl()
        );
        ProductGetDTO productGetDTO = new ProductGetDTO(
                product.getId(),
                product.getName(),
                product.getSupplier(),
                product.getPrice(),
                product.getImageUrl()
        );

        return new OrderGetDTO(id,date,city,status,userGetDTO,productGetDTO);
    }

    public OrderUpdateDTO toOrderUpdateDTO(){
        return new OrderUpdateDTO(id,date,city,status,customer.getId(), product.getId());
    }

    public Order(LocalDateTime date, String city, Status status, User customer, Product product) {
        this.date = date;
        this.city = city;
        this.status = status;
        this.customer = customer;
        this.product = product;
    }

    public Order(Long id, LocalDateTime date, String city, Status status) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.status = status;
    }
}
