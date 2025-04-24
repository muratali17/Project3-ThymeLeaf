package com.onlineshopping.project3.updateDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUpdateDTO {
    private Long id;

    private String name;

    private String supplier;

    private BigDecimal price;

    private String imageUrl = "nophoto.jpg";
}
