package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "t_price")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Price extends BaseEntity {
    private Double cost;
    private boolean isPromotional;
}
