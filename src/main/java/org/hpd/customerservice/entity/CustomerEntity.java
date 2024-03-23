package org.hpd.customerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customers")
@AllArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;
    @Column(name = "email_address", length = 100, nullable = false)
    private String emailAddress;
    @Column(name = "address", length = 225)
    private String address;
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "state", length = 25)
    private String state;
    @Column(name = "zip", length = 10)
    private String zip;

    public CustomerEntity() {
    }

}
