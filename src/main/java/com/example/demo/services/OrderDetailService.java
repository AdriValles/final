package com.example.demo.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.demo.models.OrderDetail;
import com.example.demo.models.Product;
import com.example.demo.repository.OrderDetailRepository;
import java.util.List;
import java.util.Optional;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;

    public OrderDetailService(OrderDetailRepository orderDetailRepository, ProductService productService) {
        this.orderDetailRepository = orderDetailRepository;
        this.productService = productService;
    }

    @Transactional
    public OrderDetail createOrderDetail(OrderDetail orderDetail) {
        // Validar stock
        Product product = orderDetail.getProducto();
        if (product.getStock() < orderDetail.getCantidad()) {
            throw new RuntimeException("Stock insuficiente para el producto: " + product.getNombre());
        }
        
        // Actualizar stock
        product.setStock(product.getStock() - orderDetail.getCantidad());
        productService.updateProductStock(product);
        
        // Calcular precio unitario y subtotal
        orderDetail.setPrecioUnitario(product.getPrecio());
        
        return orderDetailRepository.save(orderDetail);
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailsByPedidoId(Long pedidoId) {
        return orderDetailRepository.findByPedidoId(pedidoId);
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailsByProductoId(Long productoId) {
        return orderDetailRepository.findByProductoId(productoId);
    }

    @Transactional(readOnly = true)
    public Optional<OrderDetail> getOrderDetailById(Long id) {
        return orderDetailRepository.findById(id);
    }

    @Transactional
    public void deleteOrderDetail(Long id) {
        Optional<OrderDetail> orderDetailOpt = orderDetailRepository.findById(id);
        if (orderDetailOpt.isPresent()) {
            OrderDetail orderDetail = orderDetailOpt.get();
            // Restaurar el stock del producto
            Product product = orderDetail.getProducto();
            product.setStock(product.getStock() + orderDetail.getCantidad());
            productService.updateProductStock(product);
            
            orderDetailRepository.deleteById(id);
        }
    }

    @Transactional
    public OrderDetail updateOrderDetail(Long id, OrderDetail orderDetail) {
        Optional<OrderDetail> existingOrderDetailOpt = orderDetailRepository.findById(id);
        if (existingOrderDetailOpt.isPresent()) {
            OrderDetail existingOrderDetail = existingOrderDetailOpt.get();
            Product product = orderDetail.getProducto();
            
            // Si cambia la cantidad, actualizar stock
            if (existingOrderDetail.getCantidad() != orderDetail.getCantidad()) {
                int diferencia = orderDetail.getCantidad() - existingOrderDetail.getCantidad();
                if (product.getStock() < diferencia) {
                    throw new RuntimeException("Stock insuficiente para actualizar el pedido");
                }
                product.setStock(product.getStock() - diferencia);
                productService.updateProductStock(product);
            }
            
            existingOrderDetail.setCantidad(orderDetail.getCantidad());
            existingOrderDetail.setPrecioUnitario(product.getPrecio());
            
            return orderDetailRepository.save(existingOrderDetail);
        }
        throw new RuntimeException("Detalle de pedido no encontrado con ID: " + id);
    }
} 