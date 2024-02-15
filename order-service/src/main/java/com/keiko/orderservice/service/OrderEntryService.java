package com.keiko.orderservice.service;

import com.keiko.orderservice.request.ModificationOrderRequest;

public interface OrderEntryService {
    void saveOrderEntry (ModificationOrderRequest saveOrderEntryRequest);

    void removeOrderEntry (ModificationOrderRequest removeOrderEntryRequest);

}
