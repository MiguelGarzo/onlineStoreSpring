package com.tiendaonline.tienda.products.service;

import com.tiendaonline.tienda.exceptions.NotExistsException;
import com.tiendaonline.tienda.products.entity.Product;
import com.tiendaonline.tienda.products.repository.ProductRepository;
import com.tiendaonline.tienda.products.dto.ProductRequestDTO;
import com.tiendaonline.tienda.products.dto.ProductResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public List<ProductResponseDTO> getAll() {

        return repository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProductResponseDTO save(ProductRequestDTO pRequested){
        Product product = repository.save(toEntity(pRequested));
        return toResponse(product);
    }

    public ProductResponseDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new NotExistsException("Product with id: " + id + "not found"));
        return toResponse(product);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO uProduct){
        // Asking for a Product by its id, it will be saved at the variable cProduct to update it.
        Product cProduct = repository.findById(id).orElseThrow(() -> new NotExistsException("Product with id: " + id + "not found"));

        cProduct.setName(uProduct.getName());
        cProduct.setPrice(uProduct.getPrice());
        cProduct.setStock(uProduct.getStock());

        return toResponse(repository.save(cProduct));
    }

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