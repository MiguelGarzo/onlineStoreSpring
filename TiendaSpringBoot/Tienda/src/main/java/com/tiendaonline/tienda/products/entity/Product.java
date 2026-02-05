package com.tiendaonline.tienda.products.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

// Make a table in PostgreSQL
@Entity
@Table(name = "products")

// Make the Getters
@Getter

// Make the Setters
@Setter

// Make an empty constructor needed for JPA
@NoArgsConstructor

//Make a full constructor with all the attributes
@AllArgsConstructor
public class Product {
    
    // Define the PrimaryKey as autogenerate in PostgreSQL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    private String name;
    @NotNull(message = "Product price is required")
    @Positive(message = "Price must be more than 0")
    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;
    @NotNull(message = "Product stock is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    @Column(nullable = false)
    private Integer stock;

}
