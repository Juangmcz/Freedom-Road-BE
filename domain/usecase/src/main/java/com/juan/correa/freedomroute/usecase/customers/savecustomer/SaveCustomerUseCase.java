package com.juan.correa.freedomroute.usecase.customers.savecustomer;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class SaveCustomerUseCase implements Function<Customer, Mono<Customer>> {

    private final CustomerRepositoryGateway repositoryGateway;

    @Override
    public Mono<Customer> apply(Customer customer) {
        return repositoryGateway.saveCustomer(customer);
    }
}
