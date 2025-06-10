package org.hpd.customerservice.mapper;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerMapperTest {
  private CustomerMapper customerMapper;

  @BeforeEach
  void setUp() {
    customerMapper = new CustomerMapper();
  }

  @Test
  void testTranslateToEntity_Success() {
    CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
    createCustomerRequest.setFirstName("John");
    createCustomerRequest.setLastName("Doe");
    createCustomerRequest.setEmailAddress("john.doe@example.com");
    createCustomerRequest.setAddress("123 Main St");
    createCustomerRequest.setCity("Test City");
    createCustomerRequest.setState("Test State");
    createCustomerRequest.setZip("12345");

    CustomerEntity expectedCustomerEntity = new CustomerEntity(
        "John",
        "Doe",
        "john.doe@example.com",
        "123 Main St",
        "Test City",
        "Test State",
        "12345");

    CustomerEntity actualCustomerEntity = customerMapper.translateToEntity(createCustomerRequest);
    assertEquals(expectedCustomerEntity, actualCustomerEntity);
  }
}
