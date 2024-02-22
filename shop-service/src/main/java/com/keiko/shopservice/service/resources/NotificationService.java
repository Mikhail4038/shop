package com.keiko.shopservice.service.resources;

import com.keiko.commonservice.entity.resource.stock.ProductStockEmail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.keiko.commonservice.constants.MicroServiceConstants.NOTIFICATION_SERVICE;
import static com.keiko.commonservice.constants.WebResourceKeyConstants.EMAIL_NOTIFICATION_BASE;
import static com.keiko.shopservice.constants.WebResourceKeyConstants.PRODUCT_STOCKS;

@Service
@FeignClient (name = NOTIFICATION_SERVICE)
public interface NotificationService {

    @PostMapping (value = EMAIL_NOTIFICATION_BASE + PRODUCT_STOCKS)
    void sendProductStocks (@RequestBody ProductStockEmail productStockEmail);
}
