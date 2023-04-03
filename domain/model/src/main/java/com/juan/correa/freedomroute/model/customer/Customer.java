package com.juan.correa.freedomroute.model.customer;
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
public class Customer {

    private String id;
    private String dni;
    private String name;
    private String lastName;
    private String email;
    private String cell;
}
