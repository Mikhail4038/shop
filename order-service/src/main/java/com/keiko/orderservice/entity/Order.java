package com.keiko.orderservice.entity;

import com.keiko.orderservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@Table (name = "t_order")
@EntityListeners (TimeEntityListener.class)
public class Order extends BaseEntity {

    private Long userId;
    private Long shopId;

    @OneToMany (fetch = LAZY, cascade = {PERSIST, MERGE, REMOVE}, orphanRemoval = true)
    @JoinColumn (name = "order_id")
    private List<OrderEntry> entries = new ArrayList<> ();

    @OneToOne (cascade = {MERGE, REMOVE})
    private DeliveryAddress deliveryAddress;

    @Column (insertable = false)
    private Double deliveryCost;

    @Column (insertable = false)
    private Double totalPrice;

    @Column (insertable = false)
    private Double totalAmount;

    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus;
}
