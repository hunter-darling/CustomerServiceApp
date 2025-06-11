package org.hpd.customerservice.rest;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.manager.CustomerManager;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerRestService.class)
class CustomerRestServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CustomerManager customerManager;

  @Test
  void testGetCustomers_noCity_success() throws Exception {
    ArrayList<CustomerEntity> customers = new ArrayList<>();
    customers.add(new CustomerEntity());
    when(customerManager.getAllCustomers()).thenReturn(customers);
    mockMvc.perform(get("/customers"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetCustomers_withCity_success() throws Exception {
    ArrayList<CustomerEntity> customers = new ArrayList<>();
    customers.add(new CustomerEntity());
    when(customerManager.getCustomersByCity(anyString())).thenReturn(customers);
    mockMvc.perform(get("/customers?city=Some City"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetCustomers_noCity_failure_404() throws Exception {
    when(customerManager.getAllCustomers()).thenThrow(SQLException.class);
    mockMvc.perform(get("/customers"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetCustomers_noCity_failure_500() throws Exception {
    when(customerManager.getAllCustomers()).thenThrow(RuntimeException.class);
    mockMvc.perform(get("/customers"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void testGetCustomers_withCity_failure_404() throws Exception {
    when(customerManager.getCustomersByCity(anyString())).thenThrow(SQLException.class);
    mockMvc.perform(get("/customers?city=Some City"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetCustomers_withCity_failure_500() throws Exception {
    when(customerManager.getCustomersByCity(anyString())).thenThrow(RuntimeException.class);
    mockMvc.perform(get("/customers?city=Some City"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void testGetCustomer_success() throws Exception {
    when(customerManager.getCustomerById(anyLong())).thenReturn(new CustomerEntity());
    mockMvc.perform(get("/customers/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testGetCustomer_failure_404() throws Exception {
    when(customerManager.getCustomerById(anyLong())).thenThrow(SQLException.class);
    mockMvc.perform(get("/customers/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  void testGetCustomer_failure_500() throws Exception {
    when(customerManager.getCustomerById(anyLong())).thenThrow(RuntimeException.class);
    mockMvc.perform(get("/customers/1"))
        .andExpect(status().isInternalServerError());
  }

  @Test
  void testCreateCustomer_success() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setEmailAddress("john.doe@example.com");
    request.setCity("Test City");
    when(customerManager.addNewCustomer(any())).thenReturn(new CustomerEntity());
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  void testCreateCustomer_failure_400() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest();
    request.setFirstName("");
    request.setLastName("");
    request.setEmailAddress("");
    request.setCity("");
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void testCreateCustomer_failure_409() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setEmailAddress("john.doe@example.com");
    request.setCity("Test City");
    when(customerManager.addNewCustomer(any()))
        .thenThrow(new DataIntegrityViolationException("", new SQLException("UNIQUE constraint violated")));
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isConflict());
  }

  @Test
  void testCreateCustomer_failure_500() throws Exception {
    CreateCustomerRequest request = new CreateCustomerRequest();
    request.setFirstName("John");
    request.setLastName("Doe");
    request.setEmailAddress("john.doe@example.com");
    request.setCity("Test City");
    when(customerManager.addNewCustomer(any())).thenThrow(RuntimeException.class);
    mockMvc.perform(post("/customers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isInternalServerError());
  }
}
