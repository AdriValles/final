package com.example.demo.api;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.api.request.ProductCreationRequest;
import com.example.demo.models.User;
import com.example.demo.services.UserService;



@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final productServiceService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public User createProduct(@RequestBody ProductCreationRequest productCreationRequest){
        return productService.createProduct(productCreationRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id){
        productService.removeProduct(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return productService.getProduct(id).orElse(null);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers(){
        return productService.getAllProducts();
    }

    

}
