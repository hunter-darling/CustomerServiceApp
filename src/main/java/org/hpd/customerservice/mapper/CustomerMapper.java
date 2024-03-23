package org.hpd.customerservice.mapper;

import org.hpd.customerservice.entity.CustomerEntity;
import org.hpd.customerservice.model.CreateCustomerRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerEntity translateToEntity(CreateCustomerRequest createCustomerRequest) {
        return new CustomerEntity(null,
            createCustomerRequest.getFirstName(),
            createCustomerRequest.getLastName(),
            createCustomerRequest.getEmailAddress(),
            createCustomerRequest.getAddress(),
            createCustomerRequest.getCity(),
            createCustomerRequest.getState(),
            createCustomerRequest.getZip()
        );
    }
}
