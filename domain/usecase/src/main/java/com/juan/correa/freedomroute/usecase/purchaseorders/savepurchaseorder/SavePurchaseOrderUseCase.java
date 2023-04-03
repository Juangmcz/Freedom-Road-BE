package com.juan.correa.freedomroute.usecase.purchaseorders.savepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SavePurchaseOrderUseCase implements Function<PurchaseOrder, Mono<PurchaseOrder>> {

    private final PurchaseOrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<PurchaseOrder> apply(PurchaseOrder purchaseOrder) {
        return repositoryGateway.savePurchaseOrder(purchaseOrder);
    }
}
