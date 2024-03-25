package org.hpd.customerservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.manager.CustomerManager;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
@Valid
public class CustomerRestService {

    private final CustomerManager customerManager;

    @Autowired
    public CustomerRestService(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }


    @Operation(summary = "Get all customers or customers by city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customers found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity[].class)) }),
            @ApiResponse(responseCode = "404", description = "Customers not found",
                    content = @Content) })
    @GetMapping("/customers")
    public ResponseEntity getCustomers(@RequestParam(value = "city", required = false) String city) {
        if (city == null) {
            try {
                List<CustomerEntity> customerEntities = this.customerManager.getAllCustomers();
                return ResponseEntity.ok(customerEntities);
            } catch (SQLException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customers not found");
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            }
        } else {
            try {
                List<CustomerEntity> customerEntities = this.customerManager.getCustomersByCity(city);
                return ResponseEntity.ok(customerEntities);
            } catch (SQLException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers found in " + city);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
            }
        }
    }

    @Operation(summary = "Get a customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content) })
    @GetMapping("/customers/{customerId}")
    public ResponseEntity getCustomerById(@PathVariable("customerId") Long customerId) {
        try {
            CustomerEntity customerEntity = this.customerManager.getCustomerById(customerId);
            return ResponseEntity.ok(customerEntity);
        } catch (SQLException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerEntity.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request body: fistName, lastName, email, and city are required",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "A customer with the provided email address already exists",
                    content = @Content) })
    @PostMapping("/customers")
    public ResponseEntity addCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        try {
            CustomerEntity newCustomerEntity = this.customerManager.addNewCustomer(createCustomerRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCustomerEntity);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request body: fistName, lastName, email, and city are required");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("A customer with the provided email address already exists");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
