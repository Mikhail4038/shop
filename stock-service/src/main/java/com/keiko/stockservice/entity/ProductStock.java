package com.keiko.stockservice.entity;

import com.keiko.stockservice.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table (name = "t_product_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners (TimeEntityListener.class)
public class ProductStock extends BaseEntity {

    @Column (nullable = false)
    private String ean;

    @Column (nullable = false)
    private Long balance;

    private Long booked = 0L;

    @Column (nullable = false)
    private LocalDate expirationDate;

    @Enumerated (EnumType.STRING)
    private StopList stopList = StopList.NONE;
}
