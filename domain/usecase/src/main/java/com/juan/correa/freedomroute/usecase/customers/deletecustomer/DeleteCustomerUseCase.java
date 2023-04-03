package com.juan.correa.freedomroute.usecase.customers.deletecustomer;

import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteCustomerUseCase implements Function<String, Mono<Void>> {

    private final CustomerRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String customerId) {
        return repositoryGateway.deleteCustomer(customerId);
    }
}
