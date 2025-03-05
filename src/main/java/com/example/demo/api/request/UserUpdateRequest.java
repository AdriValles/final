package com.example.demo.api.request;

public record UserUpdateRequest(
    String nombre,
    String contrasena,
    int edad,
    boolean administrador
) {} 