package com.juan.correa.freedomroute.usecase.purchaseorders.getallpurchaseorders;

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
class GetAllPurchaseOrdersUseCaseTest {
    @Mock
    PurchaseOrderRepositoryGateway purchaseOrderRepositoryGateway;

    GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase;

    @BeforeEach
    void setUp() {
        getAllPurchaseOrdersUseCase = new GetAllPurchaseOrdersUseCase(purchaseOrderRepositoryGateway);
    }

    @Test
    @DisplayName("getAllPurchaseOrders_success")
    void getAllPurchaseOrders() {

        Mockito.when(purchaseOrderRepositoryGateway.getAllPurchaseOrders()).thenReturn(DataMocks.allPurchaseOrders());

        var result = getAllPurchaseOrdersUseCase.get();

        StepVerifier.create(result)
                .expectNextMatches(purchaseOrder -> purchaseOrder.getId().equals("1"))
                .expectNextMatches(purchaseOrder -> purchaseOrder.getId().equals("2"))
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway, Mockito.times(1)).getAllPurchaseOrders();
    }

    @Test
    @DisplayName("getAllPurchaseOrders_nonSuccess")
    void getEmptyAllPurchaseOrders() {

        Mockito.when(purchaseOrderRepositoryGateway.getAllPurchaseOrders()).thenReturn(DataMocks.emptyAllPurchaseOrders());

        var result = getAllPurchaseOrdersUseCase.get();

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway, Mockito.times(1)).getAllPurchaseOrders();
    }
}