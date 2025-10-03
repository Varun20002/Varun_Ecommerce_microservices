package com.microservice.api_gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity  // For reactive security
public class ApiGatewaySecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http
                .csrf().disable()  
                .authorizeExchange(authorize -> authorize
                        
                        .pathMatchers("/eureka/**").permitAll()
                        
                        .pathMatchers(
                                "/user-service/swagger-ui/**", "/user-service/api-docs/**", "/user-service/v3/api-docs/**", "/user-service/swagger-ui.html",
                                "/product-service/swagger-ui/**", "/product-service/api-docs/**", "/product-service/v3/api-docs/**", "/product-service/swagger-ui.html",
                                "/order-service/swagger-ui/**", "/order-service/api-docs/**", "/order-service/v3/api-docs/**", "/order-service/swagger-ui.html","v3/api-docs/**"
                        ).permitAll()
                        
                        
                        .pathMatchers(HttpMethod.GET, "/products").permitAll()
                        .pathMatchers("/products").hasRole("ADMIN")  
                        .pathMatchers("/products/**").hasRole("ADMIN")  
                        
                        .pathMatchers("/orders").hasAnyRole("CUSTOMER", "ADMIN")
                        
                        .pathMatchers("/users/**", "/orders/**", "/products/**").authenticated()
                        
                        .anyExchange().authenticated()
                )
                .httpBasic();  

        return http.build();
    }

    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("adminpass")
                .roles("ADMIN")
                .build();

        UserDetails customer = User.withDefaultPasswordEncoder()
                .username("customer")
                .password("custpass")
                .roles("CUSTOMER")
                .build();

        return new MapReactiveUserDetailsService(admin, customer);
    }
}