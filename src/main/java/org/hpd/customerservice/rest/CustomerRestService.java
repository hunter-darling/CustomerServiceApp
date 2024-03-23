package org.hpd.customerservice.rest;

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
public class CustomerRestService {

    private final CustomerManager customerManager;

    @Autowired
    public CustomerRestService(CustomerManager customerManager) {
        this.customerManager = customerManager;
    }

    //TODO: add MORE ~VERBOSE~ error conditions and handling

    @GetMapping("/customers")
    public ResponseEntity<List<CustomerEntity>> getCustomers(@RequestParam(value = "city", required = false) String city) {
        if(city == null) {
            try {
                List<CustomerEntity> customerEntities = this.customerManager.getAllCustomers();
                return ResponseEntity.ok(customerEntities);
            } catch (SQLException e) {
                return ResponseEntity.notFound().build();
            } catch (RuntimeException e) {
                return ResponseEntity.internalServerError().build();
            }
        } else {
            try {
                List<CustomerEntity> customerEntities = this.customerManager.getCustomersByCity(city);
                return ResponseEntity.ok(customerEntities);
            } catch (SQLException e) {
                return ResponseEntity.notFound().build();
            } catch (RuntimeException e) {
                return ResponseEntity.internalServerError().build();
            }
        }
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable("customerId") Long customerId) {
        try {
            CustomerEntity customerEntity = this.customerManager.getCustomerById(customerId);
            return ResponseEntity.ok(customerEntity);
        } catch (SQLException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/customers")
    public ResponseEntity addCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        try {
            CustomerEntity newCustomerEntity = this.customerManager.addNewCustomer(createCustomerRequest);
            return ResponseEntity.ok(newCustomerEntity);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
