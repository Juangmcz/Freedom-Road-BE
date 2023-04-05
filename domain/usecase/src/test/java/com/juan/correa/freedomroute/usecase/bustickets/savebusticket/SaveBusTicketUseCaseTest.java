package com.juan.correa.freedomroute.usecase.bustickets.savebusticket;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class SaveBusTicketUseCaseTest {

    @Mock
    BusTicketRepositoryGateway busTicketRepository;

    SaveBusTicketUseCase saveBusTicketUseCase;

    @BeforeEach
    void init(){
        saveBusTicketUseCase = new SaveBusTicketUseCase(busTicketRepository);
    }

    @Test
    @DisplayName("saveBusTicket_success")
    void saveBusTicket(){

        Mockito.when(busTicketRepository.saveBusTicket(ArgumentMatchers.any(BusTicket.class))).thenReturn(DataMocks.busTicket());

        var result = saveBusTicketUseCase.apply(DataMocks.rawBusTicket());

        StepVerifier.create(result)
                .expectNextMatches(busTicket -> busTicket.getId().equals("1"))
                .verifyComplete();

        Mockito.verify(busTicketRepository).saveBusTicket(ArgumentMatchers.any(BusTicket.class));
    }

    @Test
    @DisplayName("saveBusTicket_nonSuccess")
    void saveBusTicketNonSuccess(){

        Mockito.when(busTicketRepository.saveBusTicket(ArgumentMatchers.any(BusTicket.class))).thenReturn(DataMocks.emptyBusTicket());

        var result = saveBusTicketUseCase.apply(DataMocks.rawBusTicket());

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(busTicketRepository).saveBusTicket(ArgumentMatchers.any(BusTicket.class));
    }
}