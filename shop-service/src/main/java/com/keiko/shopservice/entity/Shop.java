package com.keiko.shopservice.entity;

import com.keiko.shopservice.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table (name = "t_shop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners (TimeEntityListener.class)
public class Shop extends BaseEntity {

    @OneToMany
    @JoinColumn (name = "shop_id")
    private List<ProductStock> productStock;

    @OneToOne
    private Address address;
}
