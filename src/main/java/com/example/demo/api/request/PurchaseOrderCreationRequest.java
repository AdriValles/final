package com.example.demo.api.request;

import java.util.List;

public record PurchaseOrderCreationRequest(
    Long numeroPedido, 
    String descripcion, 
    String estado, 
    Long usuarioId,
    List<OrderDetailRequest> detalles
) {}