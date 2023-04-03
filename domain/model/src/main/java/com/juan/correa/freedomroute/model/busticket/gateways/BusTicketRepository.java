package com.juan.correa.freedomroute.model.busticket.gateways;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BusTicketRepository {

    Flux<BusTicket> getAllBusTickets();
    Mono<BusTicket> getBusTicketById(String id);
    Mono<BusTicket> saveBusTicket(BusTicket busTicket);
    Mono<BusTicket> updateBusTicket(String id, BusTicket busTicket);
    Mono<Void> deleteBusTicket(String id);
}
