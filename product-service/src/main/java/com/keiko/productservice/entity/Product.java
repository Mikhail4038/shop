package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_product")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Product extends BaseEntity {

    @Column (unique = true)
    private String ean;
    private String name;
    private Double price;

    // TODO
    private Timestamp expirationDate;

    @OneToOne (cascade = {PERSIST, MERGE, REMOVE})
    private Rating rating;

    @OneToMany (fetch = LAZY, mappedBy = "product", cascade = REMOVE)
    private List<Review> reviews;

    @ManyToOne (fetch = LAZY)
    private Producer producer;
}
