package com.keiko.orderservice.service;

import com.keiko.orderservice.request.OrderEntryRequest;

public interface OrderEntryService {
    void saveOrderEntry (OrderEntryRequest saveEntryRequest);

    void removeOrderEntry (OrderEntryRequest removeEntryRequest);

}
