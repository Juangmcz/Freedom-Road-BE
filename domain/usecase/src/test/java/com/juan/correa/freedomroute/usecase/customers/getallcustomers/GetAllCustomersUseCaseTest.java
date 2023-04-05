package com.juan.correa.freedomroute.usecase.customers.getallcustomers;

import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
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
class GetAllCustomersUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    GetAllCustomersUseCase getAllCustomersUseCase;

    @BeforeEach
    void init() {
        getAllCustomersUseCase = new GetAllCustomersUseCase(customerRepository);
    }

    @Test
    @DisplayName("getAllCustomers_success")
    void get() {

        Mockito.when(customerRepository.getAllCustomers()).thenReturn(DataMocks.allCustomers());

        var result = getAllCustomersUseCase.get();

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .expectNextMatches(customer -> customer.getId().equals("2"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getAllCustomers();
    }

    @Test
    @DisplayName("getAllCustomers_nonSuccess")
    void getEmpty() {

        Mockito.when(customerRepository.getAllCustomers()).thenReturn(DataMocks.emptyAllCustomers());

        var result = getAllCustomersUseCase.get();

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getAllCustomers();
    }
}