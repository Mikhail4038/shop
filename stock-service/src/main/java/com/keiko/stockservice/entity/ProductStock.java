package com.keiko.stockservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table (name = "t_product_stock")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductStock {

    @Id
    @GeneratedValue (strategy = IDENTITY)
    private Long id;

    @Column (nullable = false)
    private String ean;

    @Column (nullable = false)
    private Double balance;

    @Column (nullable = false)
    private LocalDate expirationDate;

    @Enumerated (EnumType.STRING)
    private StopList stopList = StopList.NONE;
}
