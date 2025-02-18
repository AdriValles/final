package com.example.demo.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.demo.api.request.PurchaseOrderCreationRequest;
import com.example.demo.models.PurchaseOrder;
import com.example.demo.services.PurchaseOrderService;

@RestController
@RequestMapping("/api/v1/purchase-orders")
@CrossOrigin(origins = "*")
public class PurchaseOrderController {

    private final PurchaseOrderService purchaseOrderService;

    public PurchaseOrderController(PurchaseOrderService purchaseOrderService){
        this.purchaseOrderService = purchaseOrderService;
    }

    @PostMapping
    public PurchaseOrder createPurchaseOrder(@RequestBody PurchaseOrderCreationRequest purchaseOrderCreationRequest){
        return purchaseOrderService.createPurchaseOrder(purchaseOrderCreationRequest);
    }

    @DeleteMapping("/{id}")
    public void deletePurchaseOrder(@PathVariable Long id){
        purchaseOrderService.removePurchaseOrder(id);
    }

    @GetMapping("/{id}")
    public PurchaseOrder getPurchaseOrder(@PathVariable Long id){
        return purchaseOrderService.getPurchaseOrder(id).orElse(null);
    }

    @GetMapping("/getall")
    public List<PurchaseOrder> getAllPurchaseOrders(){
        return purchaseOrderService.getAllPurchaseOrders();
    }
}
