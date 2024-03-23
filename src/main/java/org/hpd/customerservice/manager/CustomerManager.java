package org.hpd.customerservice.manager;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.mapper.CustomerMapper;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.hpd.customerservice.repository.CustomerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

@Component
public class CustomerManager {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    public CustomerManager(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerEntity> getAllCustomers() throws SQLException {
        List<CustomerEntity> customerEntities = this.customerRepository.findAll();
        if (customerEntities == null || customerEntities.isEmpty()) {
            throw new SQLException();
        } else {
            return customerEntities;
        }
    }

    public List<CustomerEntity> getCustomersByCity(String city) throws SQLException {
        List<CustomerEntity> customerEntities = this.customerRepository.findByCity(city);
        if (customerEntities == null || customerEntities.isEmpty()) {
            throw new SQLException();
        } else {
            return customerEntities;
        }
    }

    public CustomerEntity getCustomerById(Long customerId) throws SQLException {
        CustomerEntity customerEntity = this.customerRepository.findById(customerId).orElse(null);
        if (customerEntity == null) {
            throw new SQLException();
        } else {
            return customerEntity;
        }
    }

    public CustomerEntity addNewCustomer(CreateCustomerRequest createCustomerRequest) throws DataIntegrityViolationException {
        CustomerEntity customerEntity = this.customerMapper.translateToEntity(createCustomerRequest);
        return this.customerRepository.save(customerEntity);
    }
}
