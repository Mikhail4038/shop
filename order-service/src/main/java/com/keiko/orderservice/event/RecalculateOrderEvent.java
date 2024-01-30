package com.keiko.orderservice.event;

import com.keiko.orderservice.entity.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecalculateOrderEvent {
    private Order order;

    public RecalculateOrderEvent (Order order) {
        this.order = order;
    }
}
