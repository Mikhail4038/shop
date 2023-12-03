package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table (name = "t_rating")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class Rating extends BaseEntity {
    private Float averageAssessment;
    private Integer countReviews;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Rating rating = (Rating) o;

        return new EqualsBuilder ()
                .append (averageAssessment, rating.averageAssessment)
                .append (countReviews, rating.countReviews)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (averageAssessment)
                .append (countReviews)
                .toHashCode ();
    }
}
