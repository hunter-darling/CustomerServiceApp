package org.hpd.customerservice.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCustomerRequest {
    @NotEmpty(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email address is required")
    private String emailAddress;
    private String address;
    private String city;
    private String state;
    private String zip;
}
