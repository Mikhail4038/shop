package com.keiko.productservice.entity;

import com.keiko.productservice.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_product")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Product extends BaseEntity {

    @Column (unique = true, nullable = false)
    private String ean;
    private String name;
    private Double price;

    // TODO
    private Timestamp expirationDate;

    @Transient
    // TODO dynamic attribute (reviews.assessment), implement with interceptor
    //@PostLoad
    @Column (insertable = false)
    private Double rating;

    @OneToMany (fetch = LAZY, mappedBy = "product", cascade = REMOVE)
    private List<Review> reviews;

    @ManyToOne (fetch = LAZY)
    private Producer producer;
}
