package com.juan.correa.freedomroute.model.purchaseorder;
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
public class PurchaseOrder {

    private String id;
    private String userId;
    private String status;
    private double total;
    private String purchaseDate;
    private int numberOfPassengers;
}
