package com.example.demo.api.request;

public record PurchaseOrderUpdateRequest(
    Long numeroPedido,
    String descripcion,
    double total,
    String estado,
    Long usuarioId
) {} 