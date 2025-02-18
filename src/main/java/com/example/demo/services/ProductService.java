
package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.api.request.ProductCreationRequest;
import com.example.demo.api.request.UserCreationRequest;
import com.example.demo.models.Product;
import com.example.demo.models.User;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private final ProductService productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public User createProduct(ProductCreationRequest productCreationRequest){
        return productRepository.save(mapToUser(productCreationRequest));
    }

    private User mapToProduct(ProductCreationRequest productCreationRequest){
            Product product = new Product();
            product.setNombre(productCreationRequest.nombre());
            product.setContrasena(productCreationRequest.contrasena());
            product.setEdad(productCreationRequest.edad());
            product.setAdministrador(productCreationRequest.administrador());
        return user;
    }

    public void removeUser(Long id){
        userRepository.deleteById(id);
    }

    public Optional<User> getUser(final Long id){
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
