package com.juan.correa.freedomroute.usecase.bustickets.getallbustickets;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.function.Supplier;

@RequiredArgsConstructor
public class GetAllBusTicketsUseCase implements Supplier<Flux<BusTicket>> {

    private final BusTicketRepositoryGateway repositoryGateway;

    @Override
    public Flux<BusTicket> get() {
        return repositoryGateway.getAllBusTickets();
    }
}
