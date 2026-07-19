package com.example.demo.configuration;

import com.example.demo.Service.MyLogoutHandler;
import com.example.demo.filters.JwtValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Configuration
public class AppConfig {
     private final JwtValidation jwtValidation;
     private final MyLogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/customers/login","customers/register","/products/**").permitAll();
                    auth.requestMatchers("/customers/delete","/customers/delete").hasRole("ADMIN");
                    auth.anyRequest().authenticated();
                });
        http.addFilterBefore(jwtValidation, BasicAuthenticationFilter.class);
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
   return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }



}
