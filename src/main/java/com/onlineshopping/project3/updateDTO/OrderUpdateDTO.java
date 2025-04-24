package com.onlineshopping.project3.updateDTO;

import com.onlineshopping.project3.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderUpdateDTO {

    private Long id;

    private LocalDateTime date;

    private String city;

    private Status status;

    private Long customerId;

    private Long productId;

}
