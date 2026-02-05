package com.tiendaonline.tienda.products.service;

import com.tiendaonline.tienda.exceptions.NotExistsException;
import com.tiendaonline.tienda.products.entity.Product;
import com.tiendaonline.tienda.products.repository.ProductRepository;
import com.tiendaonline.tienda.products.dto.ProductRequestDTO;
import com.tiendaonline.tienda.products.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// Making this class a Service (Just logic class, no HTTP, no SQL)
@Service
public class ProductService {
    // Creating the variable relation with our repository class
    private final ProductRepository repository;

    // Conecting our classes
    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    // Method to list all our products
    public List<ProductResponseDTO> getAll() {

        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Method to save a DTO as a product in our DB and return a DTO to the client/user
    public ProductResponseDTO save(ProductRequestDTO pRequested){
        Product product = repository.save(toEntity(pRequested));
        return toResponse(product);
    }

    // Function to find a product by its id, if there's no product found it send a Error Message.
    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotExistsException("Product with id: " + id + "not found"));
        return toResponse(product);
    }

    // Method to update a product by its id
    public ProductResponseDTO update(Long id, ProductRequestDTO uProduct){
        // Asking for a Product by its id, it will be saved at the variable cProduct to update it.
        Product cProduct = repository.findById(id).orElseThrow(() -> new NotExistsException("Product with id: " + id + "not found"));

        cProduct.setName(uProduct.getName());
        cProduct.setPrice(uProduct.getPrice());
        cProduct.setStock(uProduct.getStock());

        return toResponse(repository.save(cProduct));
    }

    // Method to delete a product by its id
    public void delete(Long id){
        Product cProduct = repository.findById(id).orElseThrow(() -> new NotExistsException("Product with id: " + id + "not found"));
        repository.delete(cProduct);
    }

    private ProductResponseDTO toResponse(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }

    private Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        return product;
    }
}