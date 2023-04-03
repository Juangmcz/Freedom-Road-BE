package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import com.juan.correa.freedomroute.mongo.data.CustomerData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CustomerMongoRepositoryAdapter implements CustomerRepositoryGateway {

    private final ObjectMapper mapper;
    private final CustomerMongoDBRepository customerRepository;

    @Override
    public Flux<Customer> getAllCustomers() {
        return this.customerRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("There are no customers registered")))
                .map(customer -> mapper.map(customer, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> getCustomerById(String id) {
        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Customer not found")))
                .map(customer -> mapper.map(customer, Customer.class));
    }

    @Override
    public Mono<Customer> getCustomerByEmail(String id) {
        return null;
    }

    @Override
    public Mono<Customer> saveCustomer(Customer customer) {
        return this.customerRepository
                .save(mapper.map(customer, CustomerData.class))
                .map(customer1 -> mapper.map(customer1, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Customer> updateCustomer(String id, Customer customer) {
        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No customer matches the provided ID")))
                .flatMap(oldCustomer ->{
                    customer.setId(oldCustomer.getId());
                    return customerRepository.save(mapper.map(customer, CustomerData.class));
                }).map(updatedCustomer -> mapper.map(updatedCustomer, Customer.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> deleteCustomer(String id) {
        return this.customerRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Customer not found")))
                .flatMap(customer -> this.customerRepository.deleteById(id))
                .onErrorResume(Mono::error);
    }
}
