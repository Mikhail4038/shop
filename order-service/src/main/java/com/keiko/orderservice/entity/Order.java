package com.keiko.orderservice.entity;

import com.keiko.orderservice.event.listener.TimeEntityListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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

    @OneToOne (cascade = {MERGE, REMOVE}, orphanRemoval = true)
    private DeliveryAddress deliveryAddress;

    @Column (insertable = false)
    private BigDecimal deliveryCost;

    @Column (insertable = false)
    private BigDecimal totalPrice;

    @Column (insertable = false)
    private BigDecimal totalAmount;

    @Enumerated (EnumType.STRING)
    private OrderStatus orderStatus;
}
