package com.juan.correa.freedomroute.usecase.bustickets.updatebusticket;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import com.juan.correa.freedomroute.usecase.DataMocks;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class UpdateBusTicketUseCaseTest {
    @Mock
    BusTicketRepositoryGateway busTicketRepository;

    UpdateBusTicketUseCase updateBusTicketUseCase;

    @BeforeEach
    void init(){
        updateBusTicketUseCase = new UpdateBusTicketUseCase(busTicketRepository);
    }

    @Test
    @DisplayName("updateBusTicket_success")
    void updateBusTicket(){

        BusTicket busTicket1 = DataMocks.rawBusTicket();

        Mockito.when(busTicketRepository.updateBusTicket("busTicketId", busTicket1))
                .thenReturn(Mono.just(busTicket1));

        var result = updateBusTicketUseCase.apply("busTicketId", busTicket1);

        StepVerifier.create(result)
                .expectNext(busTicket1)
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).updateBusTicket("busTicketId", busTicket1);
    }
}