package com.juan.correa.freedomroute.config;

import com.juan.correa.freedomroute.model.busticket.gateways.BusTicketRepositoryGateway;
import com.juan.correa.freedomroute.model.customer.gateways.CustomerRepositoryGateway;
import com.juan.correa.freedomroute.model.purchaseorder.gateways.PurchaseOrderRepositoryGateway;
import com.juan.correa.freedomroute.usecase.bustickets.deletebusticket.DeleteBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getallbustickets.GetAllBusTicketsUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.getbusticketbyid.GetBusTicketByIdUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.savebusticket.SaveBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.bustickets.updatebusticket.UpdateBusTicketUseCase;
import com.juan.correa.freedomroute.usecase.customers.deletecustomer.DeleteCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.getallcustomers.GetAllCustomersUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyemail.GetCustomerByEmailUseCase;
import com.juan.correa.freedomroute.usecase.customers.getcustomerbyid.GetCustomerByIdUseCase;
import com.juan.correa.freedomroute.usecase.customers.savecustomer.SaveCustomerUseCase;
import com.juan.correa.freedomroute.usecase.customers.updatecustomer.UpdateCustomerUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.deletepurchaseorder.DeletePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getallpurchaseorders.GetAllPurchaseOrdersUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.getpurchaseorderbyid.GetPurchaseOrderByIdUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.savepurchaseorder.SavePurchaseOrderUseCase;
import com.juan.correa.freedomroute.usecase.purchaseorders.updatepurchaseorder.UpdatePurchaseOrderUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "com.juan.correa.freedomroute.usecase")/*,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)*/
public class UseCasesConfig {

        // Customer Use Cases
        @Bean
        public GetAllCustomersUseCase getAllCustomersUseCase(CustomerRepositoryGateway gateway){
                return new GetAllCustomersUseCase(gateway);
        }

        @Bean
        public GetCustomerByIdUseCase getCustomerByIdUseCase(CustomerRepositoryGateway gateway){
                return new GetCustomerByIdUseCase(gateway);
        }

        @Bean
        public GetCustomerByEmailUseCase getCustomerByEmailUseCase(CustomerRepositoryGateway gateway){
                return new GetCustomerByEmailUseCase(gateway);
        }

        @Bean
        public SaveCustomerUseCase saveCustomerUseCase(CustomerRepositoryGateway gateway) {
                return new SaveCustomerUseCase(gateway);
        }

        @Bean
        public UpdateCustomerUseCase updateCustomerUseCase(CustomerRepositoryGateway gateway){
                return new UpdateCustomerUseCase(gateway);
        }

        @Bean
        public DeleteCustomerUseCase deleteCustomerUseCase(CustomerRepositoryGateway gateway){
                return new DeleteCustomerUseCase(gateway);
        }


        // Bus Tickets Use Cases
        @Bean
        public GetAllBusTicketsUseCase getAllBusTicketsUseCase(BusTicketRepositoryGateway gateway){
                return new GetAllBusTicketsUseCase(gateway);
        }

        @Bean
        public GetBusTicketByIdUseCase getBusTicketByIdUseCase(BusTicketRepositoryGateway gateway){
                return new GetBusTicketByIdUseCase(gateway);
        }

        @Bean
        public SaveBusTicketUseCase saveBusTicketUseCase(BusTicketRepositoryGateway gateway){
                return new SaveBusTicketUseCase(gateway);
        }

        @Bean
        public UpdateBusTicketUseCase updateBusTicketUseCase(BusTicketRepositoryGateway gateway){
                return new UpdateBusTicketUseCase(gateway);
        }

        @Bean
        public DeleteBusTicketUseCase deleteBusTicketUseCase(BusTicketRepositoryGateway gateway){
                return new DeleteBusTicketUseCase(gateway);
        }


        // Purchase Orders Use cases
        @Bean
        public GetAllPurchaseOrdersUseCase getAllPurchaseOrdersUseCase(PurchaseOrderRepositoryGateway gateway){
                return new GetAllPurchaseOrdersUseCase(gateway);
        }

        @Bean
        public GetPurchaseOrderByIdUseCase getPurchaseOrderByIdUseCase(PurchaseOrderRepositoryGateway gateway){
                return new GetPurchaseOrderByIdUseCase(gateway);
        }

        @Bean
        public SavePurchaseOrderUseCase savePurchaseOrderUseCase(PurchaseOrderRepositoryGateway gateway){
                return new SavePurchaseOrderUseCase(gateway);
        }

        @Bean
        public UpdatePurchaseOrderUseCase updatePurchaseOrderUseCase(PurchaseOrderRepositoryGateway gateway){
                return new UpdatePurchaseOrderUseCase(gateway);
        }

        @Bean
        public DeletePurchaseOrderUseCase deletePurchaseOrderUseCase(PurchaseOrderRepositoryGateway gateway){
                return new DeletePurchaseOrderUseCase(gateway);
        }
}
