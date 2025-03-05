package com.example.demo.api;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.api.request.ProductCreationRequest;
import com.example.demo.api.request.ProductUpdateRequest;
import com.example.demo.models.Product;
import com.example.demo.services.ProductService;

@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductCreationRequest productCreationRequest){
        return productService.createProduct(productCreationRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateRequest updateRequest) {
        try {
            Product updatedProduct = productService.updateProduct(id, updateRequest);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id){
        productService.removeProduct(id);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id){
        return productService.getProduct(id).orElse(null);
    }

    @GetMapping("/getall")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
}
