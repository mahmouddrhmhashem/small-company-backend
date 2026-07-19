package com.example.demo.dto;



import com.example.demo.Entity.Products;
import com.example.demo.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserDTO {
    private int Id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<Role> role;

    private Set<Products> products;

    public void setProducts(Products products){
        this.products.add(products);
    }


}
