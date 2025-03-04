package com.example.demo.api;

import org.springframework.web.bind.annotation.*;

import com.example.demo.models.Transport;
import com.example.demo.repository.TransportRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transports")
@CrossOrigin(origins = "*")
public class TransportController {

    private final TransportRepository transportRepository;

    public TransportController(TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @GetMapping
    public List<Transport> obtenerTodos() {
        return transportRepository.findAll();
    }

    @PostMapping
    public Transport createTransport(@RequestBody Transport transport) {
        return transportRepository.save(transport);
    }

    @GetMapping("/category/{categoryId}")
    public List<Transport> findByCategory(@PathVariable String categoryId) {
        return transportRepository.findByCategory(categoryId);
    }

    @GetMapping("/license/{license}")
    public Transport findByLicense(@PathVariable String license) {
        return transportRepository.findByLicense(license);
    }

    @DeleteMapping("/{id}")
    public void removeTransport(@PathVariable String id) {
        transportRepository.deleteById(id);
    }
}

