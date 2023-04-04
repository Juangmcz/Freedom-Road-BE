package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.usecase.purchaseorders.deletepurchaseorder.DeletePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getallpurchaseorders.GetAllPurchaseOrdersUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getpurchaseorderbyid.GetPurchaseOrderByIdUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.savepurchaseorder.SavePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.updatepurchaseorder.UpdatePurchaseOrderUseCase;
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
public class PurchaseOrderRouterRest {

    @Bean
    @RouterOperation(path = "/api/purchaseorders",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllPurchaseOrdersUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllPurchaseOrders",
                    tags = "Purchase Order Use Cases",
                    responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Success",
                            content = @Content(schema = @Schema(implementation = PurchaseOrder.class))),
                    @ApiResponse(responseCode = "204", description = "No orders found")}
            )
    )
    public RouterFunction<ServerResponse> getAllPurchaseOrders(GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase){
        return route(GET("/api/purchaseorders"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllPurchaseOrdersUseCase.get(), PurchaseOrder.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/purchaseorders/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetPurchaseOrderByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getPurchaseOrderById",
                    tags = "Purchase Order Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Purchase Order Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Purchase order found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = PurchaseOrder.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Order not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> getPurchaseOrderById(GetPurchaseOrderByIdUseCase getPurchaseOrderByIdUseCase){
        return route(GET("/api/purchaseorders/{id}"),
                request -> getPurchaseOrderByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(purchaseOrder -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(purchaseOrder))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    @RouterOperation(path = "/api/purchaseorders",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = SavePurchaseOrderUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "savePurchaseOrder",
                    tags = "Purchase Order Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "purchaseOrder",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = PurchaseOrder.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = PurchaseOrder.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "Save a purchase order",
                            content = @Content(schema = @Schema(implementation = PurchaseOrder.class)))
            )
    )
    public RouterFunction<ServerResponse> savePurchaseOrder(SavePurchaseOrderUseCase savePurchaseOrderUseCase) {
        return route(POST("/api/purchaseorders").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PurchaseOrder.class)
                        .flatMap(purchaseOrder -> savePurchaseOrderUseCase.apply(purchaseOrder)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/api/purchaseorders/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdatePurchaseOrderUseCase.class,
            method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "updatePurchaseOrder",
                    tags = "Purchase Order Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Purchase order Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            ),
                            @Parameter(
                                    name = "purchaseOrder",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = PurchaseOrder.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = PurchaseOrder.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(
                            required = true,
                            description = "Update a purchase order",
                            content = @Content(schema = @Schema(implementation = PurchaseOrder.class))
                    )
            )
    )
    public RouterFunction<ServerResponse> updatePurchaseOrder(UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase) {
        return route(PUT("/api/purchaseorders/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(PurchaseOrder.class)
                        .flatMap(purchaseOrder -> updatePurchaseOrderUseCase.apply(request.pathVariable("id"), purchaseOrder)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    @RouterOperation(path = "/api/purchaseorders/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeletePurchaseOrderUseCase.class,
            method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deletePurchaseOrder",
                    tags = "Purchase Order Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Purchase Order Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Purchase order deleted successfully",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = PurchaseOrder.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Order not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> deletePurchaseOrder(DeletePurchaseOrderUseCase deletePurchaseOrderUseCase){
        return route(DELETE("/api/purchaseorders/{id}"),
                request ->  deletePurchaseOrderUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
