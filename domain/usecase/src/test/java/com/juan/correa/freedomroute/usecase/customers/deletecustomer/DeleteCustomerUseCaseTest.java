package com.juan.correa.freedomroute.usecase.customers.deletecustomer;


import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
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
class DeleteCustomerUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    DeleteCustomerUseCase deleteCustomerUseCase;

    @BeforeEach
    void setUp(){
        deleteCustomerUseCase = new DeleteCustomerUseCase(customerRepository);
    }

    @Test
    @DisplayName("deleteCustomer_success")
    void deleteCustomer(){

        Mockito.when(customerRepository.deleteCustomer("customerId")).thenReturn(Mono.empty());

        var result = deleteCustomerUseCase.apply("customerId");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).deleteCustomer("customerId");
    }
}