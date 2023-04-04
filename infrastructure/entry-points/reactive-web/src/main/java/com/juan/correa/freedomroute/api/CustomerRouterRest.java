package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.usecase.customers.deletecustomer.DeleteCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.getallcustomers.GetAllCustomersUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyemail.GetCustomerByEmailUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyid.GetCustomerByIdUseCase;
import com.juan.correa.freedomroute.usecase.customers.savecustomer.SaveCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.updatecustomer.UpdateCustomerUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
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
    @RouterOperation(path = "/api/customers",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllCustomersUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllCustomers",
                    tags = "Customer Use Cases",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Customer.class))),
                            @ApiResponse(responseCode = "204", description = "No customers found")}
            )
    )
    public RouterFunction<ServerResponse> getAllCustomers(GetAllCustomersUseCase getAllCustomersUseCase){
        return route(GET("/api/customers"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllCustomersUseCase.get(), Customer.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/customers/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCustomerByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCustomerById",
                    tags = "Customer Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Customer Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Customer found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
                            )
                    }
            )
    )
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
    @RouterOperation(path = "/api/customers/email/{email}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetCustomerByEmailUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getCustomerByEmail",
                    tags = "Customer Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "email",
                                    description = "Customer email",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Customer found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
                            )
                    }
            )
    )
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
    @RouterOperation(path = "/api/customers",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveCustomerUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "saveCustomer",
                    tags = "Customer Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "customer",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Customer.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Customer.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "Save a customer",
                            content = @Content(schema = @Schema(implementation = Customer.class)))
            )
    )
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
    @RouterOperation(path = "/api/customers/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateCustomerUseCase.class,
            method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "updateCustomer",
                    tags = "Customer Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Customer Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            ),
                            @Parameter(
                                    name = "customer",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = Customer.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = Customer.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(
                            required = true,
                            description = "Update a customer",
                            content = @Content(schema = @Schema(implementation = Customer.class))
                    )
            )
    )
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
    @RouterOperation(path = "/api/customers/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteCustomerUseCase.class,
            method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteCustomer",
                    tags = "Customer Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Customer Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Customer deleted successfully",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = Customer.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Customer not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> deleteCustomer(DeleteCustomerUseCase deleteCustomerUseCase){
        return route(DELETE("/api/customers/{id}"),
                request ->  deleteCustomerUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
