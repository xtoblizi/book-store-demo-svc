package com.example.demo.domains.customer.db.models;

import com.example.demo.commons.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name = "customers")
@Entity
public class Customer extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String addressInfo; // expand this to street, area, state, country
    private String billingAddress; // simplified
    private String otherInfo;

    public Customer(String firstName, String lastName, String email, String addressInfo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.addressInfo = addressInfo;
    }

    public Customer(){}
}
