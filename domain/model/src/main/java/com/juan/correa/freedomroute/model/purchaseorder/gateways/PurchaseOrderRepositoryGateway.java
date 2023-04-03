package com.juan.correa.freedomroute.model.purchaseorder.gateways;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PurchaseOrderRepositoryGateway {

    Flux<PurchaseOrder> getAllPurchaseOrders();
    Mono<PurchaseOrder> getPurchaseOrderById(String id);
    Mono<PurchaseOrder> savePurchaseOrder(PurchaseOrder purchaseOrder);
    Mono<PurchaseOrder> updatePurchaseOrder(String id, PurchaseOrder purchaseOrder);
    Mono<Void> deletePurchaseOrder(String id);
}
