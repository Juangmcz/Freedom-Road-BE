package com.juan.correa.freedomroute.usecase.customers.getcustomerbyemail;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetCustomerByEmailUseCase implements Function<String, Mono<Customer>> {

    private final CustomerRepositoryGateway repositoryGateway;
    @Override
    public Mono<Customer> apply(String customerEmail) {
        return repositoryGateway.getCustomerByEmail(customerEmail);
    }
}
