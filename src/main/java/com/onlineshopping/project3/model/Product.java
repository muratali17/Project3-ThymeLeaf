package com.onlineshopping.project3.model;


import com.onlineshopping.project3.dtos.get.ProductGetDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=30, nullable=false)
    private String name;

    @Column(length=30, nullable=false)
    private String supplier;

    @Column(precision = 10, scale = 2 ,nullable = false)
    private BigDecimal price;

    private String imageUrl = "nophoto.jpg";

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    public ProductGetDTO toProductGetDTO() {
        return new ProductGetDTO(id, name, supplier, price, imageUrl);
    }

    public Product(String name, String supplier, BigDecimal price, String imageUrl) {
        this.name = name;
        this.supplier = supplier;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
