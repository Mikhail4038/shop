package com.keiko.orderservice.dto.converter.order;

import com.keiko.orderservice.dto.converter.AbstractToDtoConverter;
import com.keiko.orderservice.dto.model.order.OrderDto;
import com.keiko.orderservice.dto.model.order.OrderEntryDto;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.entity.resources.Product;
import com.keiko.orderservice.entity.resources.User;
import com.keiko.orderservice.service.resources.ProductService;
import com.keiko.orderservice.service.resources.UserService;
import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderToDtoConverter extends AbstractToDtoConverter<Order, OrderDto> {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    public OrderToDtoConverter () {
        super (Order.class, OrderDto.class);
    }

    @PostConstruct
    public void setUpMapping () {
        getModelMapper ().createTypeMap (Order.class, OrderDto.class)
                .setPostConverter (converter);
    }

    @Override
    public void mapSpecificFields (Order order, OrderDto dto) {
        User user = userService.fetchBy (order.getUserId ());
        dto.setUser (user);

        List<OrderEntryDto> orderEntryDto = new ArrayList<> ();
        List<OrderEntry> entries = order.getEntries ();

        for (OrderEntry entry : entries) {
            OrderEntryDto entryDto = new ModelMapper ().map (entry, OrderEntryDto.class);
            String ean = entry.getProductEan ();
            Product product = productService.findByEan (ean);
            entryDto.setProduct (product);
            orderEntryDto.add (entryDto);
        }
        dto.setEntries (orderEntryDto);
    }
}
