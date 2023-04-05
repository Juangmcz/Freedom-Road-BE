package com.juan.correa.freedomroute.usecase.purchaseorders.getpurchaseorderbyid;

import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GetPurchaseOrderByIdUseCaseTest {

    @Mock
    PurchaseOrderRepositoryGateway purchaseOrderRepositoryGateway;

    GetPurchaseOrderByIdUseCase getPurchaseOrderByIdUseCase;

    @BeforeEach
    void setUp() {
        getPurchaseOrderByIdUseCase = new GetPurchaseOrderByIdUseCase(purchaseOrderRepositoryGateway);
    }

    @Test
    @DisplayName("getPurchaseOrderById_success")
    void getPurchaseOrderById() {

        Mockito.when(purchaseOrderRepositoryGateway.getPurchaseOrderById("1")).thenReturn(DataMocks.purchaseOrder());

        var result = getPurchaseOrderByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(purchaseOrder -> purchaseOrder.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway, Mockito.times(1)).getPurchaseOrderById("1");
    }

    @Test
    @DisplayName("getPurchaseOrderById_nonSuccess")
    void getEmptyPurchaseOrderById() {

        Mockito.when(purchaseOrderRepositoryGateway.getPurchaseOrderById("1")).thenReturn(DataMocks.emptyPurchaseOrder());

        var result = getPurchaseOrderByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway, Mockito.times(1)).getPurchaseOrderById("1");
    }
}