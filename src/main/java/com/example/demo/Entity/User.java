package com.example.demo.Entity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;


@Data

@Table(name = "customer")
@Entity
public class User implements UserDetails {


    @Id
    @Column(name = "customer_id")
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "course_like",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    public Set<Products> products;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "asd",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> role;

    @Override
    public Set<Role> getAuthorities() {
        return this.role;
    }

    public void setProducts(Products products){
        this.products.add(products);
    }


    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}