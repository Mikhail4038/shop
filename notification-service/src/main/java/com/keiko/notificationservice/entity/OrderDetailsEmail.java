package com.keiko.notificationservice.entity;

import com.keiko.notificationservice.entity.resources.Order;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetailsEmail extends SimpleEmail {
    private Order order;
}
