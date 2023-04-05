package com.juan.correa.freedomroute.usecase.purchaseorders.savepurchaseorder;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SavePurchaseOrderUseCaseTest {

    @Mock
    PurchaseOrderRepositoryGateway purchaseOrderRepositoryGateway;

    SavePurchaseOrderUseCase savePurchaseOrderUseCase;

    @BeforeEach
    void setUp() {
        savePurchaseOrderUseCase = new SavePurchaseOrderUseCase(purchaseOrderRepositoryGateway);
    }

    @Test
    @DisplayName("savePurchaseOrder_success")
    void savePurchaseOrder_success() {

        Mockito.when(purchaseOrderRepositoryGateway.savePurchaseOrder(ArgumentMatchers.any(PurchaseOrder.class))).thenReturn(DataMocks.purchaseOrder());

        var result = savePurchaseOrderUseCase.apply(DataMocks.rawPurchaseOrder());

        StepVerifier.create(result)
                .expectNextMatches(purchaseOrder -> purchaseOrder.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway).savePurchaseOrder(ArgumentMatchers.any(PurchaseOrder.class));
    }

    @Test
    @DisplayName("savePurchaseOrder_nonSuccess")
    void savePurchaseOrder_nonSuccess() {

        Mockito.when(purchaseOrderRepositoryGateway.savePurchaseOrder(ArgumentMatchers.any(PurchaseOrder.class))).thenReturn(DataMocks.emptyPurchaseOrder());

        var result = savePurchaseOrderUseCase.apply(DataMocks.rawPurchaseOrder());

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(purchaseOrderRepositoryGateway).savePurchaseOrder(ArgumentMatchers.any(PurchaseOrder.class));
    }
}