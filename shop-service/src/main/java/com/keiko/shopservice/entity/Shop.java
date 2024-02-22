package com.keiko.shopservice.entity;

import com.keiko.shopservice.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.CascadeType.REMOVE;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table (name = "t_shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class Shop extends BaseEntity {

    @OneToMany (fetch = LAZY, mappedBy = "shop")
    private List<ProductStock> productStock;

    @OneToOne (cascade = {PERSIST, REMOVE}, orphanRemoval = true)
    private ShopAddress shopAddress;
}
