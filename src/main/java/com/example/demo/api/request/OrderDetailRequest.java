package com.example.demo.api.request;

public record OrderDetailRequest(
    Long productoId,
    int cantidad
) {} 