package com.onlineshopping.project3.repository;

import com.onlineshopping.project3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT * FROM products p where lower(p.name) like lower(concat('%' , :name , '%'))" , nativeQuery = true)
    List<Product> searchProductsByName(@Param("name") String name);

    @Query(value = "select exists(select 1 from products where supplier = :supplier and name = :name) " ,nativeQuery = true)
    boolean checkDuplicateSupplierNamePair(@Param("supplier") String supplier , @Param("name") String name);

    @Query(value = "select exists(select 1 from products where supplier = :supplier and name = :name and id != :id)" , nativeQuery = true)
    boolean checkDuplicateSupplierNamePairForUpdate(@Param("id") Long id , @Param("supplier") String supplier ,
                                                    @Param("name") String name);
}
