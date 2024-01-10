package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Column (unique = true, nullable = false)
    private String ean;
    private String name;
    private Timestamp expirationDate;

    @OneToOne (fetch = LAZY, cascade = {PERSIST, MERGE, REMOVE})
    private Price price;

    @OneToOne (cascade = {PERSIST, MERGE, REMOVE})
    private Rating rating;

    @OneToMany (fetch = LAZY, mappedBy = "product", cascade = REMOVE)
    private List<Review> reviews;

    @ManyToOne (fetch = LAZY)
    private Producer producer;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Product product = (Product) o;

        return new EqualsBuilder ()
                .append (ean, product.ean)
                .append (name, product.name)
                .append (expirationDate, product.expirationDate)
                .append (price, product.price)
                .append (rating, product.rating)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (ean)
                .append (name)
                .append (expirationDate)
                .append (price)
                .append (rating)
                .toHashCode ();
    }
}
