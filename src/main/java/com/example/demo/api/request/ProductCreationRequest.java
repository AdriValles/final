package com.example.demo.api.request;

public record ProductCreationRequest(String nombre, String descripcion, double precio, int stock, String imagen) {

}
