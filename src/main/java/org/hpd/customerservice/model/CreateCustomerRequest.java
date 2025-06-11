package org.hpd.customerservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// TDOD: add RegEx validation for email address

@Data
public class CreateCustomerRequest {
  @NotBlank(message = "firstName is required")
  private String firstName;

  @NotBlank(message = "lastName is required")
  private String lastName;

  @NotBlank(message = "emailAddress is required")
  private String emailAddress;

  private String address;

  @NotBlank(message = "city is required")
  private String city;

  private String state;
  private String zip;
}
