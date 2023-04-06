package com.juan.correa.freedomroute.usecase.customers.updatecustomer;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
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
class UpdateCustomerUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    UpdateCustomerUseCase updateCustomerUseCase;

    @BeforeEach
    void init(){
        updateCustomerUseCase = new UpdateCustomerUseCase(customerRepository);
    }

    @Test
    @DisplayName("updateCustomer_success")
    void updateCustomer(){

        Customer customer1 = DataMocks.rawCustomer();

        Mockito.when(customerRepository.updateCustomer("customerId", customer1))
                .thenReturn(Mono.just(customer1));

        var result = updateCustomerUseCase.apply("customerId", customer1);

        StepVerifier.create(result)
                .expectNext(customer1)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).updateCustomer("customerId", customer1);
    }


}