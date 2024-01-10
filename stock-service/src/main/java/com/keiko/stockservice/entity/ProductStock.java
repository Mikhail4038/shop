package com.keiko.stockservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private String ean;
    private Double balance;
}
