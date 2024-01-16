package com.keiko.stockservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table (name = "t_product_stock")
@Getter
@Setter
@NoArgsConstructor
public class ProductStock {

    @Id
    @GeneratedValue (strategy = IDENTITY)
    private Long id;

    @Column (unique = true)
    private String ean;
    private Double balance;
}
