package com.keiko.orderservice.dto.converter;

import com.keiko.commonservice.dto.converter.AbstractToEntityConverter;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class DtoToOrderConverter extends AbstractToEntityConverter<OrderDto, Order> {

    public DtoToOrderConverter () {
        super (OrderDto.class, Order.class);
    }

    @Override
    public void mapSpecificFields (OrderDto dto, Order order) {
    }
}
