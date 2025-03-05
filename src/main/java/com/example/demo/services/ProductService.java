package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.api.request.ProductCreationRequest;
import com.example.demo.api.request.ProductUpdateRequest;
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

    @Transactional
    public Product updateProduct(Long id, ProductUpdateRequest updateRequest) {
        Optional<Product> productOpt = productRepository.findById(id);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            product.setNombre(updateRequest.nombre());
            product.setDescripcion(updateRequest.descripcion());
            product.setPrecio(updateRequest.precio());
            product.setStock(updateRequest.stock());
            product.setImagen(updateRequest.imagen());
            return productRepository.save(product);
        }
        throw new RuntimeException("Producto no encontrado con ID: " + id);
    }

    @Transactional
    public Product updateProductStock(Product product) {
        if (product.getId() == null) {
            throw new RuntimeException("No se puede actualizar un producto sin ID");
        }
        Optional<Product> existingProduct = productRepository.findById(product.getId());
        if (existingProduct.isPresent()) {
            Product productToUpdate = existingProduct.get();
            productToUpdate.setStock(product.getStock());
            return productRepository.save(productToUpdate);
        }
        throw new RuntimeException("Producto no encontrado con ID: " + product.getId());
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
