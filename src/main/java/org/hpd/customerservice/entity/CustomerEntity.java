package org.hpd.customerservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "customers")
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "first_name", length = 50)
    @NotEmpty
    private String firstName;
    @Column(name = "last_name", length = 50)
    private String lastName;
    @Column(name = "email_address", length = 100)
    private String emailAddress;
    @Column(name = "address", length = 225)
    private String address;
    @Column(name = "city", length = 50)
    private String city;
    @Column(name = "state", length = 25)
    private String state;
    @Column(name = "zip", length = 10)
    private String zip;

    public CustomerEntity(Long id, String firstName, String lastName, String emailAddress, String address, String city, String state, String zip) {
        if (firstName == null || lastName == null || emailAddress == null || city == null
                || firstName.isEmpty() || lastName.isEmpty() || emailAddress.isEmpty() || city.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            this.customerId = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.emailAddress = emailAddress;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zip = zip;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return Objects.equals(customerId, that.customerId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(emailAddress, that.emailAddress) && Objects.equals(address, that.address) && Objects.equals(city, that.city) && Objects.equals(state, that.state) && Objects.equals(zip, that.zip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstName, lastName, emailAddress, address, city, state, zip);
    }
}
