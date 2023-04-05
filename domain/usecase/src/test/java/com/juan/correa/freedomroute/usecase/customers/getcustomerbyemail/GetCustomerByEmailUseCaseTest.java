package com.juan.correa.freedomroute.usecase.customers.getcustomerbyemail;

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
class GetCustomerByEmailUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    GetCustomerByEmailUseCase getCustomerByEmailUseCase;

    @BeforeEach
    void init() {
        getCustomerByEmailUseCase = new GetCustomerByEmailUseCase(customerRepository);
    }

    @Test
    @DisplayName("getCustomerByEmail_success")
    void getCustomerById() {

        Mockito.when(customerRepository.getCustomerByEmail("Robert@correo.com")).thenReturn(DataMocks.customer());

        var result = getCustomerByEmailUseCase.apply("Robert@correo.com");

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getEmail().equals("Robert@correo.com"))
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerByEmail("Robert@correo.com");

    }

    @Test
    @DisplayName("getCustomerByEmail_nonSuccess")
    void getEmptyCustomerById() {

        Mockito.when(customerRepository.getCustomerById("1")).thenReturn(DataMocks.emptyCustomer());

        var result = getCustomerByEmailUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(customerRepository, Mockito.times(1)).getCustomerById("1");

    }
}