package com.onlineshopping.project3.dtos.get;

import com.onlineshopping.project3.enums.Status;
import com.onlineshopping.project3.dtos.updateDTO.OrderUpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderGetDTO {

    private Long id;

    private LocalDateTime date;

    private String city;

    private Status status;

    private UserGetDTO customer;

    private ProductGetDTO product;

    public OrderGetDTO(Long id, LocalDateTime date, String city, Status status) {
        this.id = id;
        this.date = date;
        this.city = city;
        this.status = status;
    }

    public OrderUpdateDTO toOrderUpdateDTO(){
        return new OrderUpdateDTO(id,date,city,status,customer.getId(), product.getId());
    }





}
