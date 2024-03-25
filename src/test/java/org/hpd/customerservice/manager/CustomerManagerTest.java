package org.hpd.customerservice.manager;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.mapper.CustomerMapper;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.hpd.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerManagerTest {

    private CustomerRepository customerRepository;
    private CustomerMapper customerMapper;
    private CustomerManager customerManager;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        customerMapper = mock(CustomerMapper.class);
        customerManager = new CustomerManager(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomers() throws SQLException {
        List<CustomerEntity> expectedCustomers = List.of(new CustomerEntity());
        when(customerRepository.findAll()).thenReturn(expectedCustomers);
        List<CustomerEntity> actualCustomers = customerManager.getAllCustomers();
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomersByCity() throws SQLException {
        String city = "Test City";
        List<CustomerEntity> expectedCustomers = List.of(new CustomerEntity());
        when(customerRepository.findByCity(city)).thenReturn(expectedCustomers);
        List<CustomerEntity> actualCustomers = customerManager.getCustomersByCity(city);
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomerById() throws SQLException {
        Long id = 1L;
        CustomerEntity expectedCustomer = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));
        CustomerEntity actualCustomer = customerManager.getCustomerById(id);
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void addNewCustomer() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        CustomerEntity expectedCustomer = new CustomerEntity();
        when(customerMapper.translateToEntity(createCustomerRequest)).thenReturn(expectedCustomer);
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);
        CustomerEntity actualCustomer = customerManager.addNewCustomer(createCustomerRequest);
        assertEquals(expectedCustomer, actualCustomer);
    }
}
