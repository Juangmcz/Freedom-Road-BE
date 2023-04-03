package com.juan.correa.freedomroute.usecase.purchaseorders.deletepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeletePurchaseOrderUseCase implements Function<String, Mono<Void>> {

    private final PurchaseOrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String purchaseOrderId) {
        return repositoryGateway.deletePurchaseOrder(purchaseOrderId);
    }
}
