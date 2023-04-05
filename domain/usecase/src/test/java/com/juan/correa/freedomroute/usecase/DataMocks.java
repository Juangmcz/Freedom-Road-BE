package com.juan.correa.freedomroute.usecase;

import com.juan.correa.freedomroute.model.busticket.BusTicket;
import com.juan.correa.freedomroute.model.customer.Customer;
import com.juan.correa.freedomroute.model.purchaseorder.PurchaseOrder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DataMocks {

    // Customer Mocks
    public static Flux<Customer> allCustomers(){
        return Flux.just(
                new Customer(
                        "1",
                        "125159889",
                        "Austin",
                        "Santos",
                        "Austin@correo.com",
                        "3185256978"
                ),
                new Customer(
                        "2",
                        "252515687",
                        "Robert",
                        "Lincoln",
                        "Robert@correo.com",
                        "3115789645"
                )
        );
    }

    public static Flux<Customer> emptyAllCustomers(){
        return Flux.empty();
    }

    public static Mono<Customer> customer(){
        return Mono.just(
                new Customer(
                        "1",
                        "252515687",
                        "Robert",
                        "Lincoln",
                        "Robert@correo.com",
                        "3115789645"
                )
        );
    }

    public static Customer rawCustomer(){
        return new Customer(
                        "1",
                        "252515687",
                        "Robert",
                        "Lincoln",
                        "Robert@correo.com",
                        "3115789645"
        );
    }

    public static Mono<Customer> emptyCustomer(){
        return Mono.empty();
    }


    // Bus Tickets Mocks
    public static Flux<BusTicket> allBusTickets(){
        return Flux.just(
                new BusTicket(
                        "1",
                        "Medellin",
                        "Santa Jeronimo",
                        "imageUrl",
                        15.400,
                        5
                ),
                new BusTicket(
                        "2",
                        "Santa Marta",
                        "Medellin",
                        "imageUrl",
                        60.700,
                        5
                )
        );
    }

    public static Flux<BusTicket> emptyAllBusTickets(){
        return Flux.empty();
    }

    public static Mono<BusTicket> busTicket(){
        return Mono.just(
                new BusTicket(
                        "1",
                        "Medellin",
                        "Santa Jeronimo",
                        "imageUrl",
                        15.400,
                        5
                )
        );
    }

    public static BusTicket rawBusTicket(){
        return new BusTicket(
                "1",
                "Medellin",
                "Santa Jeronimo",
                "imageUrl",
                15.400,
                5
        );
    }

    public static Mono<BusTicket> emptyBusTicket(){
        return Mono.empty();
    }


    // Purchase Order Mocks
    public static Flux<PurchaseOrder> allPurchaseOrders(){
        return Flux.just(
                new PurchaseOrder(
                        "1",
                        "userId",
                        "Received",
                        20.850,
                        "04/05/2023",
                        5
                ),
                new PurchaseOrder(
                        "2",
                        "userId",
                        "Checked",
                        60.450,
                        "04/01/2023",
                        2
                )
        );
    }

    public static Flux<PurchaseOrder> emptyAllPurchaseOrders(){
        return Flux.empty();
    }

    public static Mono<PurchaseOrder> purchaseOrder(){
        return Mono.just(
                new PurchaseOrder(
                        "1",
                        "userId",
                        "Received",
                        20.850,
                        "04/05/2023",
                        5
                )
        );
    }

    public static PurchaseOrder rawPurchaseOrder(){
        return new PurchaseOrder(
                "1",
                "userId",
                "Received",
                20.850,
                "04/05/2023",
                5
        );
    }

    public static Mono<PurchaseOrder> emptyPurchaseOrder(){
        return Mono.empty();
    }
}
