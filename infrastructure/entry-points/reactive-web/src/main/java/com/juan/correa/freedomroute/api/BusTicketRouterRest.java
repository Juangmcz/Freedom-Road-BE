package com.juan.correa.freedomroute.api;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.usecase.bustickets.deletebusticket.DeleteBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getallbustickets.GetAllBusTicketsUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getbusticketbyid.GetBusTicketByIdUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.savebusticket.SaveBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.updatebusticket.UpdateBusTicketUseCase;
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
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BusTicketRouterRest {
    @Bean
    @RouterOperation(path = "/api/bustickets",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetAllBusTicketsUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "get",
            operation = @Operation(operationId = "getAllBusTickets",
                    tags = "Bus Ticket Use Cases",
                    responses = {
                            @ApiResponse(responseCode = "200",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = BusTicket.class))),
                            @ApiResponse(responseCode = "204", description = "No bus tickets found")}
            )
    )
    public RouterFunction<ServerResponse> getAllBusTickets(GetAllBusTicketsUseCase getAllBusTicketsUseCase){
        return route(GET("/api/bustickets"),
                request -> ServerResponse.status(201)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllBusTicketsUseCase.get(), BusTicket.class))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NO_CONTENT).bodyValue(throwable.getMessage())));
    }

    @Bean
    @RouterOperation(path = "/api/bustickets/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = GetBusTicketByIdUseCase.class,
            method = RequestMethod.GET,
            beanMethod = "apply",
            operation = @Operation(operationId = "getBusTicketById",
                    tags = "Bus Ticket Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Bus Ticket Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Bus Ticket  found",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = BusTicket.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Bus Ticket  not found"
                            )
                    }
            )
    )
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
    @RouterOperation(path = "/api/bustickets",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = SaveBusTicketUseCase.class,
            method = RequestMethod.POST,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "saveBusTicket",
                    tags = "Bus Ticket Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "busTicket",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = BusTicket.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = BusTicket.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(required = true,
                            description = "Save a bus ticket",
                            content = @Content(schema = @Schema(implementation = BusTicket.class)))
            )
    )
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
    @RouterOperation(path = "/api/bustickets/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = UpdateBusTicketUseCase.class,
            method = RequestMethod.PUT,
            beanMethod = "apply",
            operation = @Operation(
                    operationId = "updateBusTicket",
                    tags = "Bus Ticket Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Bus Ticket Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            ),
                            @Parameter(
                                    name = "busTicket",
                                    in = ParameterIn.PATH,
                                    schema = @Schema(implementation = BusTicket.class)
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "201",
                                    description = "Success",
                                    content = @Content(schema = @Schema(implementation = BusTicket.class))
                            ),
                            @ApiResponse(responseCode = "406", description = "Not acceptable")},
                    requestBody = @RequestBody(
                            required = true,
                            description = "Update a bus ticket",
                            content = @Content(schema = @Schema(implementation = BusTicket.class))
                    )
            )
    )
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
    @RouterOperation(path = "/api/bustickets/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE},
            beanClass = DeleteBusTicketUseCase.class,
            method = RequestMethod.DELETE,
            beanMethod = "apply",
            operation = @Operation(operationId = "deleteBusTicket",
                    tags = "Bus Ticket Use Cases",
                    parameters = {
                            @Parameter(
                                    name = "id",
                                    description = "Bus Ticket Id",
                                    required = true,
                                    in = ParameterIn.PATH
                            )
                    },
                    responses = {
                            @ApiResponse(
                                    responseCode = "200",
                                    description = "Bus ticket deleted successfully",
                                    content = @Content(
                                            schema = @Schema(
                                                    implementation = BusTicket.class
                                            )
                                    )
                            ),
                            @ApiResponse(responseCode = "404",
                                    description = "Bus ticket not found"
                            )
                    }
            )
    )
    public RouterFunction<ServerResponse> deleteBusTicket(DeleteBusTicketUseCase deleteBusTicketUseCase){
        return route(DELETE("/api/bustickets/{id}"),
                request ->  deleteBusTicketUseCase.apply(request.pathVariable("id"))
                        .flatMap(result -> ServerResponse.status(204)
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result))
                        .onErrorResume(throwable -> ServerResponse.status(HttpStatus.NOT_FOUND).build()));
    }
}
