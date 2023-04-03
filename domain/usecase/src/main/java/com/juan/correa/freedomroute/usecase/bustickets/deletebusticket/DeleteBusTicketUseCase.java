package com.juan.correa.freedomroute.usecase.bustickets.deletebusticket;

import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@RequiredArgsConstructor
public class DeleteBusTicketUseCase implements Function<String, Mono<Void>> {

    private final BusTicketRepositoryGateway repositoryGateway;

    @Override
    public Mono<Void> apply(String busTicketId) {
        return repositoryGateway.deleteBusTicket(busTicketId);
    }
}
