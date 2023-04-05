package com.juan.correa.freedomroute.usecase.customers.getcustomerbyid;

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
class GetCustomerByIdUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    GetCustomerByIdUseCase getCustomerByIdUseCase;

    @BeforeEach
    void init() {
        getCustomerByIdUseCase = new GetCustomerByIdUseCase(customerRepository);
    }

    @Test
    @DisplayName("getCustomerById_success")
    void getCustomerById() {

        Mockito.when(customerRepository.getCustomerById("1")).thenReturn(DataMocks.customerById());

        var result = getCustomerByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById("1");

    }

    @Test
    @DisplayName("getCustomerById_nonSuccess")
    void getEmptyCustomerById() {

        Mockito.when(customerRepository.getCustomerById("1")).thenReturn(DataMocks.emptyCustomerById());

        var result = getCustomerByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById("1");

    }
}