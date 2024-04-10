package com.example.demo.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
        .formLogin(login -> login
            .loginProcessingUrl("/admin/signin")
            .loginPage("/admin/signin") 
            .usernameParameter("email") 
            .passwordParameter("password")
            .defaultSuccessUrl("/admin/contacts", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/admin/logout") // ログアウトエンドポイントのURLを指定
            .logoutSuccessUrl("/admin/signin")
        )
        .authorizeHttpRequests(authz -> authz
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .requestMatchers("/admin/signup").permitAll()
            .requestMatchers("/admin/contacts").permitAll()
            .requestMatchers("/admin/contactForm").permitAll()
            .requestMatchers("/admin/confirmation").permitAll()
            .requestMatchers("/admin/complete").permitAll()
            .requestMatchers("/admin/contacts/delete").permitAll()
            .requestMatchers("/admin/deleteComplete").permitAll()
            .requestMatchers("/admin/contacts/{id}").permitAll()
            .requestMatchers("/admin/contacts/{id}/edit").permitAll()
            .requestMatchers("/admin/logout").permitAll()
           
            .anyRequest().authenticated()
        );
            return http.build();
}
}


