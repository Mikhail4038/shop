package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "t_producer_address")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Address extends BaseEntity {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;
}
