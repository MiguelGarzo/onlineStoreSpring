package com.tiendaonline.tienda.products.repository;

import com.tiendaonline.tienda.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}