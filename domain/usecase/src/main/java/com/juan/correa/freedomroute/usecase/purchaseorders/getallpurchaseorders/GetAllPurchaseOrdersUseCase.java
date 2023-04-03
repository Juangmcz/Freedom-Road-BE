package com.juan.correa.freedomroute.usecase.purchaseorders.getallpurchaseorders;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllPurchaseOrdersUseCase implements Supplier<Flux<PurchaseOrder>> {

    private final PurchaseOrderRepositoryGateway repositoryGateway;

    @Override
    public Flux<PurchaseOrder> get() {
        return repositoryGateway.getAllPurchaseOrders();
    }
}
