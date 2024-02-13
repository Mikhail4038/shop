package com.keiko.orderservice.entity;

import com.keiko.orderservice.event.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table (name = "t_delivery_address")
@EntityListeners (TimeEntityListener.class)
public class DeliveryAddress extends BaseEntity {
    private String street;
    private String house;
    private String city;
    private String country;
    private String zipCode;
}
