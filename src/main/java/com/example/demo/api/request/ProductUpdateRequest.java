package com.example.demo.api.request;

public record ProductUpdateRequest(
    String nombre,
    String descripcion,
    double precio,
    int stock,
    String imagen
) {} 