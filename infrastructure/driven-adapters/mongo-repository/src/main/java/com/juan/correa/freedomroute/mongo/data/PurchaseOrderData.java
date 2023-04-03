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
@Document(collection = "purchaseorders")
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderData {
    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    @NotBlank(message="Empty field error")
    @NotNull(message ="User ID field is required")
    private String userId;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Status field is required")
    private String status;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Total field is required")
    private double total;
    private String purchaseDate;
    private int numberOfPassengers;
}
