package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_producer")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Producer extends BaseEntity {
    private String name;

    @OneToOne (fetch = LAZY, cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private Address producerAddress;

    @OneToMany (fetch = LAZY, mappedBy = "producer")
    private List<Product> products;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Producer producer = (Producer) o;

        return new EqualsBuilder ()
                .append (name, producer.name)
                .append (producerAddress, producer.producerAddress)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (name)
                .append (producerAddress)
                .toHashCode ();
    }
}
