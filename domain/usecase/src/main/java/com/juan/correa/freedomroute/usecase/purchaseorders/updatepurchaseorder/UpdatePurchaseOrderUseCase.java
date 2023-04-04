package com.juan.correa.freedomroute.usecase.purchaseorders.updatepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdatePurchaseOrderUseCase implements BiFunction<String, PurchaseOrder, Mono<PurchaseOrder>> {

    private final PurchaseOrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<PurchaseOrder> apply(String id, PurchaseOrder purchaseOrder) {
        return repositoryGateway.updatePurchaseOrder(id, purchaseOrder);
    }
}
