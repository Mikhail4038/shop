package com.keiko.orderservice.dto.converter;

import com.keiko.commonservice.dto.converter.AbstractToDtoConverter;
import com.keiko.commonservice.entity.resource.Shop;
import com.keiko.commonservice.entity.resource.product.Product;
import com.keiko.commonservice.entity.resource.user.User;
import com.keiko.orderservice.dto.model.OrderDto;
import com.keiko.orderservice.dto.model.OrderEntryDto;
import com.keiko.orderservice.entity.Order;
import com.keiko.orderservice.entity.OrderEntry;
import com.keiko.orderservice.service.resources.ProductService;
import com.keiko.orderservice.service.resources.ShopService;
import com.keiko.orderservice.service.resources.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderToDtoConverter extends AbstractToDtoConverter<Order, OrderDto> {

    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

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
        Long userId = order.getUserId ();
        Long shopId = order.getShopId ();
        User user = userService.fetchBy (userId);
        Shop shop = shopService.fetchBy (shopId);
        dto.setUser (user);
        dto.setShop (shop);

        List<OrderEntryDto> orderEntryDto = new ArrayList<> ();
        List<OrderEntry> entries = order.getEntries ();

        for (OrderEntry entry : entries) {
            OrderEntryDto entryDto = getModelMapper ().map (entry, OrderEntryDto.class);
            String ean = entry.getProductEan ();
            Product product = productService.findByEan (ean);
            entryDto.setProduct (product);
            orderEntryDto.add (entryDto);
        }
        dto.setEntries (orderEntryDto);
    }
}
