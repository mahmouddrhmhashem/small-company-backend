package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "products")
public class Products {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(
            strategy = GenerationType.AUTO)
    private int Id;

    @Column(name = "name")
    private String Name;
    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

}
