package com.juan.correa.freedomroute.mongo.data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@Document(collection = "bustickets")
@NoArgsConstructor
@AllArgsConstructor
public class BusTicketData {
    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    @NotBlank(message="Empty field error")
    @NotNull(message ="Origin field is required")
    private String origin;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Destination field is required")
    private String destination;
    private String imageUrl;
    @NotNull(message ="Price field is required")
    private Double price;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Number of tickets field is required")
    private Integer numberOfTickets;
}
