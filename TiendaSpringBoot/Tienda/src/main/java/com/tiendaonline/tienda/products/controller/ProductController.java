package com.tiendaonline.tienda.products.controller;

import com.tiendaonline.tienda.products.service.ProductService;
import com.tiendaonline.tienda.products.dto.ProductRequestDTO;
import com.tiendaonline.tienda.products.dto.ProductResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
// Makes the endpoint to /products
@RequestMapping("/products")
public class ProductController {
    // Creates the varialbe who will contain a ProductService instance
    private final ProductService service;

    // Makes the relation beetween Controller and Service
    public ProductController(ProductService service) {

        this.service = service;
    }

    // Maps the GET method to list products(I tried with PostMan)
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }

    // Maps the GET method to list products by the required id (I tried with PostMan)
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    // Maps the POST method to create products (I tried with PostMan)
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {

        ProductResponseDTO response = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    // Maps the PUT method to update products (I tried with PostMan)
    // You have to insert your URL with the /id of the product you want to update, then make a RAW file with the updates.
    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        return service.update(id, product);
    }

    // Mapping DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}