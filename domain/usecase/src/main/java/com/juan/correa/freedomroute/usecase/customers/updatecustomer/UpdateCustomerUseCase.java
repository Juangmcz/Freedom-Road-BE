package com.juan.correa.freedomroute.usecase.customers.updatecustomer;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateCustomerUseCase implements BiFunction<String, Customer, Mono<Customer>> {

    private final CustomerRepositoryGateway repositoryGateway;

    @Override
    public Mono<Customer> apply(String id, Customer customer) {
        return repositoryGateway.updateCustomer(id, customer);
    }
}
