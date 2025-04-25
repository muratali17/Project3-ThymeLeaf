package com.onlineshopping.project3.model;

import com.onlineshopping.project3.enums.Role;
import com.onlineshopping.project3.dtos.get.UserGetDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 16, nullable = false)
    private String name;

    @Column(length = 32, nullable = false)
    private String address;

    @Column(length = 16, nullable = false)
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String imageUrl = "nophoto.jpg";


    public UserGetDTO toUserGetDTO() {
        return new UserGetDTO(id,name,address,phone,username,password,role,imageUrl);
    }

    public User(String name, String address, String phone, String username, String password, Role role, String imageUrl) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.role = role;
        this.imageUrl = imageUrl;
    }
}