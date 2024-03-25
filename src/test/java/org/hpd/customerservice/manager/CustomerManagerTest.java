package org.hpd.customerservice.manager;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.mapper.CustomerMapper;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.hpd.customerservice.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
    void getAllCustomers_success() throws SQLException {
        List<CustomerEntity> expectedCustomers = List.of(new CustomerEntity());
        when(customerRepository.findAll()).thenReturn(expectedCustomers);
        List<CustomerEntity> actualCustomers = customerManager.getAllCustomers();
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomersByCity_success() throws SQLException {
        String city = "Test City";
        List<CustomerEntity> expectedCustomers = List.of(new CustomerEntity());
        when(customerRepository.findByCity(city)).thenReturn(expectedCustomers);
        List<CustomerEntity> actualCustomers = customerManager.getCustomersByCity(city);
        assertEquals(expectedCustomers, actualCustomers);
    }

    @Test
    void getCustomerById_success() throws SQLException {
        Long id = 1L;
        CustomerEntity expectedCustomer = new CustomerEntity();
        when(customerRepository.findById(id)).thenReturn(Optional.of(expectedCustomer));
        CustomerEntity actualCustomer = customerManager.getCustomerById(id);
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void addNewCustomer_success() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        CustomerEntity expectedCustomer = new CustomerEntity();
        when(customerMapper.translateToEntity(createCustomerRequest)).thenReturn(expectedCustomer);
        when(customerRepository.save(expectedCustomer)).thenReturn(expectedCustomer);
        CustomerEntity actualCustomer = customerManager.addNewCustomer(createCustomerRequest);
        assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void testGetAllCustomers_failure() {
        when(customerRepository.findAll()).thenReturn(null);
        assertThrows(SQLException.class, () -> customerManager.getAllCustomers());
    }

    @Test
    void testGetCustomersByCity_failure() {
        String city = "Test City";
        when(customerRepository.findByCity(anyString())).thenReturn(null);
        assertThrows(SQLException.class, () -> customerManager.getCustomersByCity(city));
    }

    @Test
    void testGetCustomerById_failure() {
        Long id = 1L;
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(SQLException.class, () -> customerManager.getCustomerById(id));
    }

    @Test
    void testAddNewCustomer_failure() {
        CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
        // set request fields here
        when(customerMapper.translateToEntity(createCustomerRequest)).thenThrow(DataIntegrityViolationException.class);
        assertThrows(DataIntegrityViolationException.class, () -> customerManager.addNewCustomer(createCustomerRequest));
    }
}
