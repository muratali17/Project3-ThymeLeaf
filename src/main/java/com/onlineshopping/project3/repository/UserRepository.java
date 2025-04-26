package com.onlineshopping.project3.repository;

import com.onlineshopping.project3.dtos.get.UserGetDTO;
import com.onlineshopping.project3.enums.Role;
import com.onlineshopping.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM customers c WHERE lower(c.name) like lower(concat('%' , :name , '%')) " , nativeQuery = true)
    List<UserGetDTO> searchUsersByName(@Param("name") String name);

    boolean existsByPhone(String phone);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByRole(Role role);
}
