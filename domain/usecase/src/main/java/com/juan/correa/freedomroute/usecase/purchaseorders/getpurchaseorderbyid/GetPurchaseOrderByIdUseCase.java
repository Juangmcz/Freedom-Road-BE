package com.juan.correa.freedomroute.usecase.purchaseorders.getpurchaseorderbyid;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetPurchaseOrderByIdUseCase implements Function<String, Mono<PurchaseOrder>> {

    private final PurchaseOrderRepositoryGateway repositoryGateway;

    @Override
    public Mono<PurchaseOrder> apply(String id) {
        return repositoryGateway.getPurchaseOrderById(id);
    }
}
