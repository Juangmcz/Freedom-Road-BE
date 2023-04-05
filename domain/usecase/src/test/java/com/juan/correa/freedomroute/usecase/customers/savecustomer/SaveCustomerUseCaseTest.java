package com.juan.correa.freedomroute.usecase.customers.savecustomer;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
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
class SaveCustomerUseCaseTest {

    @Mock
    CustomerRepositoryGateway customerRepository;

    SaveCustomerUseCase saveCustomerUseCase;

    @BeforeEach
    void init(){
        saveCustomerUseCase = new SaveCustomerUseCase(customerRepository);
    }

    @Test
    @DisplayName("saveCustomer_success")
    void saveCustomer(){

        Mockito.when(customerRepository.saveCustomer(ArgumentMatchers.any(Customer.class))).thenReturn(DataMocks.customer());

        var result = saveCustomerUseCase.apply(DataMocks.rawCustomer());

        StepVerifier.create(result)
                .expectNextMatches(customer -> customer.getId().equals("1"))
                .verifyComplete();
        Mockito.verify(customerRepository).saveCustomer(ArgumentMatchers.any(Customer.class));
    }
}