package org.hpd.customerservice.rest;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.manager.CustomerManager;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRestServiceTest {

  private CustomerRestService customerRestService;
  private CustomerManager customerManager;

  @BeforeEach
  void setUp() {
    customerManager = mock(CustomerManager.class);
    customerRestService = new CustomerRestService(customerManager);
  }

  @Test
  void testGetCustomers_noCity_success() throws SQLException {
    ArrayList<CustomerEntity> customers = new ArrayList<>();
    customers.add(new CustomerEntity());
    when(customerManager.getAllCustomers()).thenReturn(customers);
    ResponseEntity<List<CustomerEntity>> customersResponseEntities = new ResponseEntity<>(List.of(new CustomerEntity()),
        null, 200);
    assertEquals(customersResponseEntities, customerRestService.getCustomers(null));
    assertDoesNotThrow(() -> customerRestService.getCustomers(null));
  }

  @Test
  void testGetCustomers_withCity_success() throws SQLException {
    ArrayList<CustomerEntity> customers = new ArrayList<>();
    customers.add(new CustomerEntity());
    when(customerManager.getCustomersByCity(anyString())).thenReturn(customers);
    ResponseEntity<List<CustomerEntity>> customersResponseEntities = new ResponseEntity<>(List.of(new CustomerEntity()),
        null, 200);
    assertEquals(customersResponseEntities, customerRestService.getCustomers("Some City"));
    assertDoesNotThrow(() -> customerRestService.getCustomers("Some City"));
  }

  @Test
  void testGetCustomers_noCity_failure_404() throws SQLException {
    when(customerManager.getAllCustomers()).thenThrow(SQLException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 404);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomers(null).getStatusCode());
  }

  @Test
  void testGetCustomers_noCity_failure_500() throws SQLException {
    when(customerManager.getAllCustomers()).thenThrow(RuntimeException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 500);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomers(null).getStatusCode());
  }

  @Test
  void testGetCustomers_withCity_failure_404() throws SQLException {
    when(customerManager.getCustomersByCity(anyString())).thenThrow(SQLException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 404);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomers("Some City").getStatusCode());
  }

  @Test
  void testGetCustomers_withCity_failure_500() throws SQLException {
    when(customerManager.getCustomersByCity(anyString())).thenThrow(RuntimeException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 500);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomers("Some City").getStatusCode());
  }

  @Test
  void testGetCustomer_success() throws SQLException {
    when(customerManager.getCustomerById(anyLong())).thenReturn(new CustomerEntity());
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 200);
    assertEquals(customerResponseEntity, customerRestService.getCustomerById(1L));
    assertDoesNotThrow(() -> customerRestService.getCustomerById(1L));
  }

  @Test
  void testGetCustomer_failure_404() throws SQLException {
    when(customerManager.getCustomerById(anyLong())).thenThrow(SQLException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 404);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomerById(1L).getStatusCode());
  }

  @Test
  void testGetCustomer_failure_500() throws SQLException {
    when(customerManager.getCustomerById(anyLong())).thenThrow(RuntimeException.class);
    ResponseEntity<CustomerEntity> customerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 500);
    assertEquals(customerResponseEntity.getStatusCode(), customerRestService.getCustomerById(1L).getStatusCode());
  }

  @Test
  void testCreateCustomer_success() {
    CreateCustomerRequest request = new CreateCustomerRequest();
    when(customerManager.addNewCustomer(request)).thenReturn(new CustomerEntity());
    ResponseEntity<CustomerEntity> newCustomerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 201);
    assertEquals(newCustomerResponseEntity, customerRestService.addCustomer(request));
    assertDoesNotThrow(() -> customerRestService.addCustomer(request));
  }

  @Test
  void testCreateCustomer_failure_400() {
    CreateCustomerRequest request = new CreateCustomerRequest();
    when(customerManager.addNewCustomer(request)).thenThrow(IllegalArgumentException.class);
    ResponseEntity<CustomerEntity> newCustomerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 400);
    assertEquals(newCustomerResponseEntity.getStatusCode(), customerRestService.addCustomer(request).getStatusCode());
  }

  @Test
  void testCreateCustomer_failure_409() {
    CreateCustomerRequest request = new CreateCustomerRequest();
    DataIntegrityViolationException dataIntegrityViolationException = new DataIntegrityViolationException("",
        new SQLException("UNIQUE constraint violated"));
    when(customerManager.addNewCustomer(request)).thenThrow(dataIntegrityViolationException);
    ResponseEntity<CustomerEntity> newCustomerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 409);
    assertEquals(newCustomerResponseEntity.getStatusCode(), customerRestService.addCustomer(request).getStatusCode());
  }

  @Test
  void testCreateCustomer_failure_500() {
    CreateCustomerRequest request = new CreateCustomerRequest();
    when(customerManager.addNewCustomer(request)).thenThrow(RuntimeException.class);
    ResponseEntity<CustomerEntity> newCustomerResponseEntity = new ResponseEntity<>(new CustomerEntity(), null, 500);
    assertEquals(newCustomerResponseEntity.getStatusCode(), customerRestService.addCustomer(request).getStatusCode());
  }
}
