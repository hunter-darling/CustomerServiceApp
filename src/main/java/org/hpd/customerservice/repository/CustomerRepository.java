package org.hpd.customerservice.repository;

import org.hpd.customerservice.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    Customer findCustomerByCustomerId(Long customerId);
}
