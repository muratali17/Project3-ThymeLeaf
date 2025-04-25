package com.onlineshopping.project3.repository;

import com.onlineshopping.project3.getDTO.UserGetDTO;
import com.onlineshopping.project3.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM customers c WHERE lower(c.name) like lower(concat('%' , :name , '%')) " , nativeQuery = true)
    List<UserGetDTO> searchUsersByName(@Param("name") String name);

    User findByUsername(String userName);

    boolean existsByPhone(String phone);
}
