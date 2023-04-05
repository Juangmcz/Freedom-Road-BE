package com.juan.correa.freedomroute.usecase.bustickets.getbusticketbyid;

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
class GetBusTicketByIdUseCaseTest {

    @Mock
    BusTicketRepositoryGateway busTicketRepository;

    GetBusTicketByIdUseCase getBusTicketByIdUseCase;

    @BeforeEach
    void init(){
        getBusTicketByIdUseCase = new GetBusTicketByIdUseCase(busTicketRepository);
    }

    @Test
    @DisplayName("getBusTicketById_success")
    void getBusTicketById(){

        Mockito.when(busTicketRepository.getBusTicketById("1")).thenReturn(DataMocks.busTicket());

        var result = getBusTicketByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextMatches(busTicket -> busTicket.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).getBusTicketById("1");
    }

    @Test
    @DisplayName("getBusTicketById_nonSuccess")
    void getEmptyBusTicketById(){

        Mockito.when(busTicketRepository.getBusTicketById("1")).thenReturn(DataMocks.emptyBusTicket());

        var result = getBusTicketByIdUseCase.apply("1");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).getBusTicketById("1");
    }
}