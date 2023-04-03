package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import com.juan.correa.freedomroute.mongo.data.PurchaseOrderData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class PurchaseOrderMongoRepositoryAdapter implements PurchaseOrderRepositoryGateway {

    private final PurchaseOrderMongoDBRepository purchaseOrderRepository;
    private final ObjectMapper mapper;

    @Override
    public Flux<PurchaseOrder> getAllPurchaseOrders() {
        return this.purchaseOrderRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("There are no purchase orders registered")))
                .map(purchaseOrder -> mapper.map(purchaseOrder, PurchaseOrder.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<PurchaseOrder> getPurchaseOrderById(String id) {
        return this.purchaseOrderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Purchase order not found")))
                .map(purchaseOrder -> mapper.map(purchaseOrder, PurchaseOrder.class));
    }

    @Override
    public Mono<PurchaseOrder> savePurchaseOrder(PurchaseOrder purchaseOrder) {
        return this.purchaseOrderRepository
                .save(mapper.map(purchaseOrder, PurchaseOrderData.class))
                .map(purchaseOrder1 -> mapper.map(purchaseOrder1, PurchaseOrder.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<PurchaseOrder> updatePurchaseOrder(String id, PurchaseOrder purchaseOrder) {
        return this.purchaseOrderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No purchase order matches the provided ID")))
                .flatMap(oldPurchaseOrder ->{
                    purchaseOrder.setId(oldPurchaseOrder.getId());
                    return purchaseOrderRepository.save(mapper.map(purchaseOrder, PurchaseOrderData.class));
                }).map(updatedPurchaseOrder -> mapper.map(updatedPurchaseOrder, PurchaseOrder.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> deletePurchaseOrder(String id) {
        return this.purchaseOrderRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Purchase order not found")))
                .flatMap(purchaseOrder -> this.purchaseOrderRepository.deleteById(id))
                .onErrorResume(Mono::error);
    }
}
