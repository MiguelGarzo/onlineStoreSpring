package com.tiendaonline.tienda.products.repository;

import com.tiendaonline.tienda.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// Makes the connectivity with the DB
public interface ProductRepository extends JpaRepository<Product, Long> {

}