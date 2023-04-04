package com.juan.correa.freedomroute.usecase.bustickets.getbusticketbyid;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class GetBusTicketByIdUseCase implements Function<String, Mono<BusTicket>> {

    private final BusTicketRepositoryGateway repositoryGateway;

    @Override
    public Mono<BusTicket> apply(String id) {
        return repositoryGateway.getBusTicketById(id);
    }
}
