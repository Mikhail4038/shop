package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_review", uniqueConstraints = {
        @UniqueConstraint (columnNames = {"user", "product_id"})})
@Getter
@Setter
@EntityListeners (TimeEntityListener.class)
public class Review extends BaseEntity {
    private Long user;

    @Column (nullable = false)
    private String message;

    @Column (nullable = false)
    @Min (value = 0, message = "assessment must be at least 0")
    @Max (value = 10, message = "assessment should be no more than 10")
    private byte assessment;

    @ManyToOne (fetch = LAZY)
    private Product product;
}
