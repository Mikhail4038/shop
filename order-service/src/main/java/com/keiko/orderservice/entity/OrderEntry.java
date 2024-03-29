package com.keiko.orderservice.entity;

import com.keiko.commonservice.entity.BaseEntity;
import com.keiko.commonservice.listener.TimeEntityListener;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "t_order_entry")
@EntityListeners (TimeEntityListener.class)
public class OrderEntry extends BaseEntity {
    private String productEan;
    private Long quantity;
}
