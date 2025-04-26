package com.onlineshopping.project3.security;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService, DataSource dataSource) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/", "/auth/**",
                                "/logout","/images/**",
                                "/access-denied"
                        ).permitAll()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers(
                                "/user/my-account","user/update/my-account",
                                "/user/images/**","/product/**",
                                "/order/my-order/all","/order/details/{id}",
                                "/order/add/{id}","/order/add-specific-product"
                        ).hasAnyRole("ADMIN","CUSTOMER")
                        .requestMatchers(
                                "/user/**", "/order/**"
                        ).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/access-denied"))

                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .defaultSuccessUrl("/", true)   // Login olursan direk ana sayfaya yolla
                        .permitAll()
                )
                .rememberMe(rm -> rm
                        .rememberMeParameter("remember-me")
                        .tokenRepository(persistentTokenRepository(dataSource))
                        .userDetailsService(userDetailsService)
                        .tokenValiditySeconds(3600)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository(DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        //Eğer gerekli table dbde yoksa ilk çalıştırışta uncommentleyin.
        //Fakat sonradan tekrar commentlemek gerekir yoksa hata alırsınız.
        //repository.setCreateTableOnStartup(true);
        repository.setDataSource(dataSource);
        return repository;
    }




}
