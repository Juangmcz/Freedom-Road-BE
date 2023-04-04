package com.juan.correa.freedomroute.usecase.bustickets.updatebusticket;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@RequiredArgsConstructor
public class UpdateBusTicketUseCase implements BiFunction<String, BusTicket, Mono<BusTicket>> {

    private final BusTicketRepositoryGateway repositoryGateway;

    @Override
    public Mono<BusTicket> apply(String id, BusTicket busTicket) {
        return repositoryGateway.updateBusTicket(id, busTicket);
    }
}
