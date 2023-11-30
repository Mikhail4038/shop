package com.keiko.productservice.entity;

import com.keiko.productservice.event.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
