package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.api.request.PurchaseOrderCreationRequest;
import com.example.demo.models.PurchaseOrder;
import com.example.demo.repository.PurchaseOrderRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrderCreationRequest purchaseOrderCreationRequest) {
        return purchaseOrderRepository.save(mapToPurchaseOrder(purchaseOrderCreationRequest));
    }

    private PurchaseOrder mapToPurchaseOrder(PurchaseOrderCreationRequest createRequest) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setNumeroPedido(createRequest.numeroPedido());
        purchaseOrder.setDescripcion(createRequest.descripcion());
        purchaseOrder.setTotal(createRequest.total());
        purchaseOrder.setEstado(createRequest.estado());
        purchaseOrder.setUsuario(createRequest.usuario());
        return purchaseOrder;
    }

    public void removePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    public Optional<PurchaseOrder> getPurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
}
