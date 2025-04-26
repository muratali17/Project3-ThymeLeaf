package com.onlineshopping.project3.dtos.add;

import com.onlineshopping.project3.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddDTO {


    private String name;

    private String address;

    private String phone;

    private String username;

    private String password;

    private Role role;

    private String imageUrl = "nophoto.jpg";

}
