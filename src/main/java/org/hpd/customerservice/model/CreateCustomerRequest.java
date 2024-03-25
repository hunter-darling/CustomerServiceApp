package org.hpd.customerservice.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;
    private String city;
    private String state;
    private String zip;
}
