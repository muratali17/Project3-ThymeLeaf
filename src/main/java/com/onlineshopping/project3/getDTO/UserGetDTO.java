package com.onlineshopping.project3.getDTO;

import com.onlineshopping.project3.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserGetDTO {


    private Long id;

    private String name;

    private String address;

    private String phone;

    private String username;

    private String password;

    private Role role;

    private String imageUrl = "nophoto.jpg";

}
