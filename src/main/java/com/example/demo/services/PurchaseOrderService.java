package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.api.request.PurchaseOrderCreationRequest;
import com.example.demo.models.PurchaseOrder;
import com.example.demo.models.User;
import com.example.demo.repository.PurchaseOrderRepository;
import com.example.demo.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UserRepository userRepository;

    public PurchaseOrderService(PurchaseOrderRepository purchaseOrderRepository, UserRepository userRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.userRepository = userRepository;
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

        // Obtener el usuario por ID
        Optional<User> usuarioOpt = userRepository.findById(createRequest.usuarioId());
        if (usuarioOpt.isPresent()) {
            purchaseOrder.setUsuario(usuarioOpt.get());
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }

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