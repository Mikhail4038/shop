package com.keiko.productservice.jobs;

import com.keiko.productservice.entity.Product;
import com.keiko.productservice.service.product.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SetUpPromotionJob {

    @Value ("${price.daysForPromo}")
    private byte daysForPromo;

    @Autowired
    private ProductServiceImpl productService;

    @Scheduled (cron = "0 0 12 * * *")
    public void setUpPromotion () {
        List<Product> products = productService.findProductExpirationDateSoon (daysForPromo);
        products.stream ()
                .peek (product -> product.getPrice ().setPromotional (true))
                .forEach (productService::save);
    }
}
