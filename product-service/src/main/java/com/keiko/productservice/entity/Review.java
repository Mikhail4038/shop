package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_review", uniqueConstraints = {
        @UniqueConstraint (columnNames = {"userId", "product_id"})})
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Review extends BaseEntity {

    @Column (nullable = false)
    private Long userId;

    @Column (nullable = false)
    private String message;

    @Column (nullable = false)
    @Min (value = 0, message = "assessment must be at least 0")
    @Max (value = 10, message = "assessment should be no more than 10")
    private Integer assessment;

    @ManyToOne (fetch = LAZY, optional = false)
    private Product product;

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;

        if (o == null || getClass () != o.getClass ()) return false;

        Review review = (Review) o;

        return new EqualsBuilder ()
                .append (assessment, review.assessment)
                .append (userId, review.userId)
                .append (message, review.message)
                .isEquals ();
    }

    @Override
    public int hashCode () {
        return new HashCodeBuilder (17, 37)
                .append (userId)
                .append (message)
                .append (assessment)
                .toHashCode ();
    }
}
