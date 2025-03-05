package com.example.demo.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.models.OrderDetail;
import com.example.demo.services.OrderDetailService;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orderDetails")
@CrossOrigin(origins = "*")
public class OrderDetailController {
    private final OrderDetailService orderDetailService;

    public OrderDetailController(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    @PostMapping
    public ResponseEntity<OrderDetail> createOrderDetail(@Valid @RequestBody OrderDetail orderDetail) {
        return ResponseEntity.ok(orderDetailService.createOrderDetail(orderDetail));
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByPedidoId(@PathVariable Long pedidoId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailsByPedidoId(pedidoId));
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<List<OrderDetail>> getOrderDetailsByProductoId(@PathVariable Long productoId) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailsByProductoId(productoId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
        return orderDetailService.getOrderDetailById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @Valid @RequestBody OrderDetail orderDetail) {
        try {
            OrderDetail updatedOrderDetail = orderDetailService.updateOrderDetail(id, orderDetail);
            return ResponseEntity.ok(updatedOrderDetail);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.ok().build();
    }
} 