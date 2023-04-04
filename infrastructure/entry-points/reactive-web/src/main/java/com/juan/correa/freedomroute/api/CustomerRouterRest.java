package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.usecase.customers.deletecustomer.DeleteCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.getallcustomers.GetAllCustomersUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyemail.GetCustomerByEmailUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyid.GetCustomerByIdUseCase;
import com.juan.correa.freedomroute.usecase.customers.savecustomer.SaveCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.updatecustomer.UpdateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CustomerRouterRest {
    @Bean
    public RouterFunction<ServerResponse> getAllCustomers(GetAllCustomersUseCase getAllCustomersUseCase){
        return route(GET("/api/customers"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCustomersUseCase.get(), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getCustomerById(GetCustomerByIdUseCase getCustomerByIdUseCase){
        return route(GET("/api/customers/{id}"),
                request -> getCustomerByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(customer -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(customer))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> getCustomerByEmail(GetCustomerByEmailUseCase getCustomerByEmailUseCase){
        return route(GET("/api/customers/email/{email}"),
                request -> getCustomerByEmailUseCase.apply(request.pathVariable("email"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(customer -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(customer))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveCustomer(SaveCustomerUseCase saveCustomerUseCase) {
        return route(POST("/api/customers").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Customer.class)
                        .flatMap(customer -> saveCustomerUseCase.apply(customer)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateCustomer(UpdateCustomerUseCase updateCustomerUseCase) {
        return route(PUT("/api/customers/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(Customer.class)
                        .flatMap(customer -> updateCustomerUseCase.apply(request.pathVariable("id"), customer)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteCustomer(DeleteCustomerUseCase deleteCustomerUseCase){
        return route(DELETE("/api/customers/{id}"),
                request ->  deleteCustomerUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
