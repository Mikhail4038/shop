package com.keiko.productservice.jobs;

import com.keiko.productservice.service.product.ProductExpirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeleteExpiredProductJob {

    @Autowired
    private ProductExpirationService productService;

    @Scheduled (cron = "0 0 12 * * *")
    public void deleteExpiredProducts () {
        productService.deleteExpiredProduct ();
    }
}
