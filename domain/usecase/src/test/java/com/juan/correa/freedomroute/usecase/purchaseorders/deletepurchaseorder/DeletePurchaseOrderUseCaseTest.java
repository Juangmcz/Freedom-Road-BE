package com.juan.correa.freedomroute.usecase.purchaseorders.deletepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
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
class DeletePurchaseOrderUseCaseTest {

    @Mock
    PurchaseOrderRepositoryGateway purchaseOrderRepository;

    DeletePurchaseOrderUseCase deletePurchaseOrderUseCase;

    @BeforeEach
    void setUp(){
        deletePurchaseOrderUseCase = new DeletePurchaseOrderUseCase(purchaseOrderRepository);
    }

    @Test
    @DisplayName("deletePurchaseOrder_success")
    void deletePurchaseOrder(){

        Mockito.when(purchaseOrderRepository.deletePurchaseOrder("purchaseOrderId")).thenReturn(Mono.empty());

        var result = deletePurchaseOrderUseCase.apply("purchaseOrderId");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(purchaseOrderRepository, Mockito.times(1)).deletePurchaseOrder("purchaseOrderId");
    }
}