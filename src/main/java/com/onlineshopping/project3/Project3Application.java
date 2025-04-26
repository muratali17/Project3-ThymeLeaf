package com.onlineshopping.project3;

import com.onlineshopping.project3.enums.Role;
import com.onlineshopping.project3.model.Product;
import com.onlineshopping.project3.model.User;
import com.onlineshopping.project3.repository.ProductRepository;
import com.onlineshopping.project3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@SpringBootApplication
@RequiredArgsConstructor
public class Project3Application implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(Project3Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Admin kullanıcı yoksa oluştur
        if (!userRepository.existsByRole(Role.ADMIN)) {
            String password = passwordEncoder.encode("admin");

            User admin = new User(
                    "admin name",
                    "admin address",
                    "123456789",
                    "admin",
                    password,
                    Role.ADMIN,
                    "nophoto.jpg"
            );

            userRepository.save(admin);
        }

        if (!userRepository.existsByRole(Role.CUSTOMER)) {
            String password = passwordEncoder.encode("customer");

            User admin = new User(
                    "customer name",
                    "customer address",
                    "123456789",
                    "customer",
                    password,
                    Role.CUSTOMER,
                    "nophoto.jpg"
            );

            userRepository.save(admin);
        }


        if (productRepository.count() == 0) {
            for (int i = 1; i <= 20; i++) {
                Product sampleProduct = new Product(
                        "Sample Product " + i,
                        "Sample Supplier " + i,
                        BigDecimal.valueOf(10 * i),
                        "nophoto.jpg"
                );

                productRepository.save(sampleProduct);
            }
        }
    }
}
