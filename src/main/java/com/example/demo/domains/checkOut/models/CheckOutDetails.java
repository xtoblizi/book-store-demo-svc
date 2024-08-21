package com.example.demo.domains.checkOut.models;

import com.example.demo.commons.entities.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "check_out_details")
@Entity
public class CheckOutDetails extends BaseEntity {
    
}
