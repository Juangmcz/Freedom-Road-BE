package com.juan.correa.freedomroute.model.customer.gateways;

import com.juan.correa.freedomroute.model.customer.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerRepositoryGateway {

    Flux<Customer> getAllCustomers();
    Mono<Customer> getCustomerById(String id);
    Mono<Customer> getCustomerByEmail(String email);
    Mono<Customer> saveCustomer(Customer customer);
    Mono<Customer> updateCustomer(String id, Customer customer);
    Mono<Void> deleteCustomer(String id);
}
