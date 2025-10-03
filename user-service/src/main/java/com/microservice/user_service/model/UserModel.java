package com.microservice.user_service.model;  // Adjust if needed

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @ElementCollection
    private List<Long> ordersList = new ArrayList<>();  // List of order IDs

    // No-arg constructor (required for Jackson/JPA)
    public UserModel() {}

    // All-arg constructor
    public UserModel(Long id, String name, Role role, List<Long> ordersList) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.ordersList = ordersList;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Long> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Long> ordersList) {
        this.ordersList = ordersList;
    }
}

enum Role {
    CUSTOMER, ADMIN
}