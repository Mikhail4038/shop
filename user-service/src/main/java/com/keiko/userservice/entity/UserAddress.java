package com.keiko.userservice.entity;

import com.keiko.commonservice.entity.BaseEntity;
import com.keiko.commonservice.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table (name = "t_user_address")
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class UserAddress extends BaseEntity {
    private String street;
    private String house;
    private String city;
    private String country;
    private String postcode;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        UserAddress address = (UserAddress) o;

        return new EqualsBuilder ()
                .append (street, address.street)
                .append (house, address.house)
                .append (city, address.city)
                .append (country, address.country)
                .append (postcode, address.postcode)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (street)
                .append (house)
                .append (city)
                .append (country)
                .append (postcode)
                .toHashCode ();
    }
}
