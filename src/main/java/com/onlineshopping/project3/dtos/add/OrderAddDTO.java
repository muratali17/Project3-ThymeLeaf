package com.onlineshopping.project3.dtos.add;

import com.onlineshopping.project3.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderAddDTO {
    private LocalDateTime date;

    private String city;

    private Status status;

    private Long customerId;

    private Long productId;

}
