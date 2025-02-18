package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.api.request.ProductCreationRequest;
import com.example.demo.models.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductCreationRequest productCreationRequest) {
        return productRepository.save(mapToProduct(productCreationRequest));
    }
    
    private Product mapToProduct(ProductCreationRequest createRequest) {
        Product product = new Product();
        product.setNombre(createRequest.nombre());
        product.setDescripcion(createRequest.descripcion());
        product.setPrecio(createRequest.precio());
        product.setStock(createRequest.stock());
        product.setImagen(createRequest.imagen());
        return product;
    }

    public void removeProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> getProduct(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    
}
