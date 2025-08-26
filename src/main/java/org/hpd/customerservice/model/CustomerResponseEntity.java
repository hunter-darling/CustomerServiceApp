package org.hpd.customerservice.model;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class CustomerResponseEntity extends ResponseEntity<Object> {

  public CustomerResponseEntity(HttpStatusCode status) {
    super(status);
  }

  public CustomerResponseEntity(Object body, HttpStatusCode status) {
    super(body, status);
  }

}
