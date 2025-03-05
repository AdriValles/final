package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.api.request.PurchaseOrderCreationRequest;
import com.example.demo.api.request.PurchaseOrderUpdateRequest;
import com.example.demo.api.request.OrderDetailRequest;
import com.example.demo.models.*;
import com.example.demo.repository.*;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    public PurchaseOrderService(
            PurchaseOrderRepository purchaseOrderRepository,
            UserRepository userRepository,
            ProductRepository productRepository,
            OrderDetailRepository orderDetailRepository) {
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @Transactional
    public PurchaseOrder createPurchaseOrder(PurchaseOrderCreationRequest request) {
        // Validar request
        validatePurchaseOrderRequest(request);

        // Crear el pedido
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setNumeroPedido(request.numeroPedido());
        purchaseOrder.setDescripcion(request.descripcion());
        purchaseOrder.setEstado(request.estado());

        // Asignar usuario
        User user = userRepository.findById(request.usuarioId())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.usuarioId()));
        purchaseOrder.setUser(user);

        // Guardar el pedido primero para tener el ID
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        // Procesar los detalles del pedido
        double total = 0;
        for (OrderDetailRequest detailRequest : request.detalles()) {
            Product product = productRepository.findById(detailRequest.productoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + detailRequest.productoId()));

            // Verificar stock
            if (product.getStock() < detailRequest.cantidad()) {
                throw new RuntimeException("Stock insuficiente para el producto: " + product.getNombre());
            }

            // Crear detalle
            OrderDetail detail = new OrderDetail(purchaseOrder, product, detailRequest.cantidad());
            detail = orderDetailRepository.save(detail);

            // Actualizar stock
            product.setStock(product.getStock() - detailRequest.cantidad());
            productRepository.save(product);

            // Actualizar total
            total += detail.getSubtotal();
        }

        // Actualizar total del pedido
        purchaseOrder.setTotal(total);
        return purchaseOrderRepository.save(purchaseOrder);
    }

    private void validatePurchaseOrderRequest(PurchaseOrderCreationRequest request) {
        if (request == null) {
            throw new RuntimeException("La solicitud no puede ser nula");
        }
        if (request.numeroPedido() == null) {
            throw new RuntimeException("El número de pedido no puede ser nulo");
        }
        if (request.descripcion() == null || request.descripcion().trim().isEmpty()) {
            throw new RuntimeException("La descripción no puede estar vacía");
        }
        if (request.estado() == null || request.estado().trim().isEmpty()) {
            throw new RuntimeException("El estado no puede estar vacío");
        }
        if (!isValidEstado(request.estado())) {
            throw new RuntimeException("Estado no válido. Estados válidos: PENDIENTE, EN_PROCESO, COMPLETADO, CANCELADO");
        }
        if (request.usuarioId() == null) {
            throw new RuntimeException("El ID de usuario no puede ser nulo");
        }
        if (request.detalles() == null || request.detalles().isEmpty()) {
            throw new RuntimeException("El pedido debe tener al menos un producto");
        }
    }

    private boolean isValidEstado(String estado) {
        return estado.equals("PENDIENTE") || 
               estado.equals("EN_PROCESO") || 
               estado.equals("COMPLETADO") || 
               estado.equals("CANCELADO");
    }

    @Transactional
    public PurchaseOrder updatePurchaseOrder(Long id, PurchaseOrderUpdateRequest updateRequest) {
        Optional<PurchaseOrder> orderOpt = purchaseOrderRepository.findById(id);
        if (orderOpt.isPresent()) {
            PurchaseOrder order = orderOpt.get();
            order.setNumeroPedido(updateRequest.numeroPedido());
            order.setDescripcion(updateRequest.descripcion());
            order.setTotal(updateRequest.total());
            order.setEstado(updateRequest.estado());

            if (updateRequest.usuarioId() != null) {
                Optional<User> userOpt = userRepository.findById(updateRequest.usuarioId());
                userOpt.ifPresent(order::setUser);
            }

            return purchaseOrderRepository.save(order);
        }
        throw new RuntimeException("Pedido no encontrado con ID: " + id);
    }

    @Transactional
    public void removePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<PurchaseOrder> getPurchaseOrder(Long id) {
        return purchaseOrderRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
}