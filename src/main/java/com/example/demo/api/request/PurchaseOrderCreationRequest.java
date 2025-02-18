package com.example.demo.api.request;

public record PurchaseOrderCreationRequest(Long numeroPedido, String descripcion, double total, String estado, String usuario) {

}
