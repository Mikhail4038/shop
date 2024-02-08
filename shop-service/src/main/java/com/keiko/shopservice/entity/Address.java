package com.keiko.shopservice.entity;

import com.keiko.shopservice.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "t_shop_address")
@EntityListeners (TimeEntityListener.class)
public class Address extends BaseEntity {
    private String street;
    private String house;
    private String city;
    private String country;
    private String zipCode;
}
