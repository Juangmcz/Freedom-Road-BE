package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.usecase.bustickets.deletebusticket.DeleteBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getallbustickets.GetAllBusTicketsUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getbusticketbyid.GetBusTicketByIdUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.savebusticket.SaveBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.updatebusticket.UpdateBusTicketUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BusTicketRouterRest {
    @Bean
    public RouterFunction<ServerResponse> getAllBusTickets(GetAllBusTicketsUseCase getAllBusTicketsUseCase){
        return route(GET("/api/bustickets"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBusTicketsUseCase.get(), BusTicket.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    public RouterFunction<ServerResponse> getBusTicketById(GetBusTicketByIdUseCase getBusTicketByIdUseCase){
        return route(GET("/api/bustickets/{id}"),
                request -> getBusTicketByIdUseCase.apply(request.pathVariable("id"))
                        .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                        .flatMap(busTicket -> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(busTicket))
                        .onErrorResume(throwable -> ServerResponse.notFound().build()));
    }

    @Bean
    public RouterFunction<ServerResponse> saveBusTicket(SaveBusTicketUseCase saveBusTicketUseCase) {
        return route(POST("/api/bustickets").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BusTicket.class)
                        .flatMap(busTicket -> saveBusTicketUseCase.apply(busTicket)
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> updateBusTicket(UpdateBusTicketUseCase updateBusTicketUseCase) {
        return route(PUT("/api/bustickets/{id}").and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(BusTicket.class)
                        .flatMap(busTicket -> updateBusTicketUseCase.apply(request.pathVariable("id"), busTicket)
                                .switchIfEmpty(Mono.error(new Throwable(HttpStatus.NO_CONTENT.toString())))
                                .flatMap(result -> ServerResponse.status(201)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(result))
                                .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_ACCEPTABLE).bodyValue(throwable.getMessage()))));
    }

    @Bean
    public RouterFunction<ServerResponse> deleteBusTicket(DeleteBusTicketUseCase deleteBusTicketUseCase){
        return route(DELETE("/api/bustickets/{id}"),
                request ->  deleteBusTicketUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
