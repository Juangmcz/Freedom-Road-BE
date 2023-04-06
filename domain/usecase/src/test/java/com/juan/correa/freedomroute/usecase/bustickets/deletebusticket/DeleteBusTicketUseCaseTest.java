package com.juan.correa.freedomroute.usecase.bustickets.deletebusticket;

import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
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
class DeleteBusTicketUseCaseTest {

    @Mock
    BusTicketRepositoryGateway busTicketRepository;

    DeleteBusTicketUseCase deleteBusTicketUseCase;

    @BeforeEach
    void setUp(){
        deleteBusTicketUseCase = new DeleteBusTicketUseCase(busTicketRepository);
    }

    @Test
    @DisplayName("deleteBusTicket_success")
    void deleteBusTicket(){

        Mockito.when(busTicketRepository.deleteBusTicket("busTicketId")).thenReturn(Mono.empty());

        var result = deleteBusTicketUseCase.apply("busTicketId");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();

        Mockito.verify(busTicketRepository, Mockito.times(1)).deleteBusTicket("busTicketId");
    }
}