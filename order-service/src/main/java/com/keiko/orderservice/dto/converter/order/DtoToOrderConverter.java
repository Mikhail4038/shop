package com.keiko.orderservice.dto.converter.order;

import com.keiko.orderservice.dto.converter.AbstractToEntityConverter;
import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class DtoToOrderConverter extends AbstractToEntityConverter<OrderDto, Order> {

    public DtoToOrderConverter () {
        super (OrderDto.class, Order.class);
    }

    @Override
    protected void mapSpecificFields (OrderDto dto, Order order) {
    }
}
