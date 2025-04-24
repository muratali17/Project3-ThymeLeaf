package com.onlineshopping.project3.model;


import com.onlineshopping.project3.getDTO.ProductGetDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

import java.math.BigDecimal;
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

    @Column(length=16, nullable=false)
    private String name;

    @Column(length=16, nullable=false)
    private String supplier;

    @Column(precision = 10, scale = 2 ,nullable = false)
    private BigDecimal price;

    private String imageUrl = "nophoto.jpg";

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;


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
