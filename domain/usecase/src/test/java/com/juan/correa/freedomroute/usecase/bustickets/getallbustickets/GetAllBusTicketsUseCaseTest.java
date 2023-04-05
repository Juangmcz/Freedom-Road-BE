package com.juan.correa.freedomroute.usecase.bustickets.getallbustickets;

import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class GetAllBusTicketsUseCaseTest {

    @Mock
    BusTicketRepositoryGateway busTicketRepository;

    GetAllBusTicketsUseCase getAllBusTicketsUseCase;

    @BeforeEach
    void init(){
        getAllBusTicketsUseCase = new GetAllBusTicketsUseCase(busTicketRepository);
    }

    @Test
    @DisplayName("getAllBusTickets_success")
    void getAllBusTickets() {

        Mockito.when(busTicketRepository.getAllBusTickets()).thenReturn(DataMocks.allBusTickets());

        var result = getAllBusTicketsUseCase.get();

        StepVerifier.create(result)
                .expectNextMatches(busticket -> busticket.getId().equals("1"))
                .expectNextMatches(busticket -> busticket.getId().equals("2"))
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).getAllBusTickets();
    }

    @Test
    @DisplayName("getAllBusTickets_nonSuccess")
    void getEmptyAllBusTickets() {

        Mockito.when(busTicketRepository.getAllBusTickets()).thenReturn(DataMocks.emptyAllBusTickets());

        var result = getAllBusTicketsUseCase.get();

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).getAllBusTickets();
    }
}