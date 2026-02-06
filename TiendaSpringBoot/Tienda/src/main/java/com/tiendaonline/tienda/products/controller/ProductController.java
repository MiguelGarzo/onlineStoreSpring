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
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    public ProductController(ProductService service) {

        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {

        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {

        ProductResponseDTO response = service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDTO product) {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}