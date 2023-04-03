package com.juan.correa.freedomroute.usecase.customers.getallcustomers;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Function;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllCustomersUseCase implements Supplier<Flux<Customer>> {

    private final CustomerRepositoryGateway repositoryGateway;

    @Override
    public Flux<Customer> get() {
        return repositoryGateway.getAllCustomers();
    }
}
