package com.juan.correa.freedomroute.model.busticket;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BusTicket {

    private String id;
    private String origin;
    private String destination;
    private String imageUrl;
    private Double price;
    private Integer numberOfTickets;
}
