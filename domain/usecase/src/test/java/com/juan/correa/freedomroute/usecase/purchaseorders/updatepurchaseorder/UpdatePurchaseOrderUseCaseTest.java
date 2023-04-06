package com.juan.correa.freedomroute.usecase.purchaseorders.updatepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdatePurchaseOrderUseCaseTest {

    @Mock
    PurchaseOrderRepositoryGateway purchaseOrderRepository;

    UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase;

    @BeforeEach
    void init(){
        updatePurchaseOrderUseCase = new UpdatePurchaseOrderUseCase(purchaseOrderRepository);
    }

    @Test
    @DisplayName("updatePurchaseOrder_success")
    void updatePurchaseOrder(){

        PurchaseOrder purchaseOrder1 = DataMocks.rawPurchaseOrder();

        Mockito.when(purchaseOrderRepository.updatePurchaseOrder("purchaseOrderId", purchaseOrder1))
                .thenReturn(Mono.just(purchaseOrder1));

        var result = updatePurchaseOrderUseCase.apply("purchaseOrderId", purchaseOrder1);

        StepVerifier.create(result)
                .expectNext(purchaseOrder1)
                .verifyComplete();

        Mockito.verify(purchaseOrderRepository, Mockito.times(1)).updatePurchaseOrder("purchaseOrderId", purchaseOrder1);
    }
}