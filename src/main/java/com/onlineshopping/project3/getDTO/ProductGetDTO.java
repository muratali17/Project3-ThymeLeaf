package com.onlineshopping.project3.getDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductGetDTO {

    private Long id;

    private String name;

    private String supplier;

    private BigDecimal price;

    private String imageUrl = "nophoto.jpg";


}
