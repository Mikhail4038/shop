package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_producer")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Producer extends BaseEntity {
    private String name;

    @OneToOne (fetch = LAZY, cascade = PERSIST, orphanRemoval = true)
    private Address address;

    @OneToMany (fetch = LAZY, mappedBy = "producer")
    private List<Product> products;
}
