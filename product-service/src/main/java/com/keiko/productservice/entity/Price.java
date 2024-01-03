package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table (name = "t_price")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Price extends BaseEntity {

    @Column (name = "cost")
    private Double value;
    private boolean isPromotional;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Price price = (Price) o;

        return new EqualsBuilder ()
                .append (isPromotional, price.isPromotional)
                .append (value, price.value)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (value)
                .append (isPromotional)
                .toHashCode ();
    }
}
