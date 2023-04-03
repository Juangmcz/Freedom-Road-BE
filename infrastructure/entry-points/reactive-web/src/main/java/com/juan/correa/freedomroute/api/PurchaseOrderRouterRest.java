package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import com.juan.correa.freedomroute.usecase.purchaseorders.deletepurchaseorder.DeletePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getallpurchaseorders.GetAllPurchaseOrdersUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getpurchaseorderbyid.GetPurchaseOrderByIdUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.savepurchaseorder.SavePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.updatepurchaseorder.UpdatePurchaseOrderUseCase;
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
public class PurchaseOrderRouterRest {

    @Bean
    public RouterFunction<ServerResponse> getAllPurchaseOrders(GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase){
        return route(GET("/api/purchaseorders"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllPurchaseOrdersUseCase.get(), PurchaseOrder.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
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
    public RouterFunction<ServerResponse> deletePurchaseOrder(DeletePurchaseOrderUseCase deletePurchaseOrderUseCase){
        return route(DELETE("/api/purchaseorders/{id}"),
                request ->  deletePurchaseOrderUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
