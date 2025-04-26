package com.onlineshopping.project3.dtos.add;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterDto {

    private String name;

    private String username;

    private String password;

    private String phone;

    private String address;


}
