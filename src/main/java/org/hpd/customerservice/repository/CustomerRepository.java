package org.hpd.customerservice.repository;

import org.hpd.customerservice.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {

  @NonNull
  List<CustomerEntity> findAll();

  List<CustomerEntity> findByCity(String city);
}
