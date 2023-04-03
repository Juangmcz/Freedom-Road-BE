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
@Document(collection = "customers")
@NoArgsConstructor
@AllArgsConstructor
public class CustomerData {
    @Id
    private String id = UUID.randomUUID().toString().substring(0,10);
    @NotBlank(message="Empty field error")
    @NotNull(message ="DNI field is required")
    private String dni;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Name field is required")
    private String name;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Last Name field is required")
    private String lastName;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Email field is required")
    private String email;
    @NotBlank(message="Empty field error")
    @NotNull(message ="Cellphone field is required")
    private String cell;
}
