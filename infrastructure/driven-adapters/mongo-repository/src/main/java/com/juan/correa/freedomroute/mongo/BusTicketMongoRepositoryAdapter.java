package com.juan.correa.freedomroute.mongo;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import com.juan.correa.freedomroute.mongo.data.BusTicketData;
import lombok.RequiredArgsConstructor;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class BusTicketMongoRepositoryAdapter implements BusTicketRepositoryGateway{

    private final BusTicketMongoDBRepository busTicketRepository;
    private final ObjectMapper mapper;

    @Override
    public Flux<BusTicket> getAllBusTickets() {
        return this.busTicketRepository
                .findAll()
                .switchIfEmpty(Mono.error(new Throwable("There are no tickets available")))
                .map(busTicket -> mapper.map(busTicket, BusTicket.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<BusTicket> getBusTicketById(String id) {
        return this.busTicketRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Ticket not found")))
                .map(busTicket -> mapper.map(busTicket, BusTicket.class));
    }

    @Override
    public Mono<BusTicket> saveBusTicket(BusTicket busTicket) {
        return this.busTicketRepository
                .save(mapper.map(busTicket, BusTicketData.class))
                .map(busTicket1 -> mapper.map(busTicket1, BusTicket.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<BusTicket> updateBusTicket(String id, BusTicket busTicket) {
        return this.busTicketRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("No ticket matches the provided ID")))
                .flatMap(oldBusTicket ->{
                    busTicket.setId(oldBusTicket.getId());
                    return busTicketRepository.save(mapper.map(busTicket, BusTicketData.class));
                }).map(updatedBusTicket -> mapper.map(updatedBusTicket, BusTicket.class))
                .onErrorResume(Mono::error);
    }

    @Override
    public Mono<Void> deleteBusTicket(String id) {
        return this.busTicketRepository
                .findById(id)
                .switchIfEmpty(Mono.error(new Throwable("Bus ticket not found")))
                .flatMap(busTicket -> this.busTicketRepository.deleteById(id))
                .onErrorResume(Mono::error);
    }
}
