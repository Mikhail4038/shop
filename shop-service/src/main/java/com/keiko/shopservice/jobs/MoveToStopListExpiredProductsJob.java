package com.keiko.shopservice.jobs;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.StopList;
import com.keiko.shopservice.entity.resources.ProductStockEmail;
import com.keiko.shopservice.properties.EmailProperties;
import com.keiko.shopservice.service.resources.NotificationService;
import com.keiko.shopservice.service.ProductStockService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.*;

@Component
@Log4j2
public class MoveToStopListExpiredProductsJob {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailProperties emailProperties;

    @Scheduled (cron = "0 0 0 * * *")
    public void execute () {
        Callable callable = () -> {
            List<ProductStock> expiredProductStocks = findExpiredProductStocks ();
            addToStopList (expiredProductStocks);
            return expiredProductStocks;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor ();
        Future<List<ProductStock>> future = executorService.submit (callable);

        try {
            List<ProductStock> productStocks = future.get ();
            if (!productStocks.isEmpty ()) {
                ProductStockEmail data = ProductStockEmail.builder ()
                        .toAddress (emailProperties.getAdminEmail ())
                        .subject ("Move products stock to Stop list")
                        .message ("Stock next products moved to Stop list, because they're expired.")
                        .productStocks (productStocks)
                        .build ();
                notificationService.sendProductStocks (data);
            }
        } catch (InterruptedException ex) {
            log.error (ex.getMessage ());
        } catch (ExecutionException ex) {
            log.error (ex.getMessage ());
        }
    }

    private List<ProductStock> findExpiredProductStocks () {
        return productStockService.findProductStocksToMoveExpiredStopList ();
    }

    private void addToStopList (List<ProductStock> expiredProductStocks) {
        expiredProductStocks.forEach (stock -> stock.setStopList (StopList.EXPIRED));
        productStockService.saveAll (expiredProductStocks);
    }
}
