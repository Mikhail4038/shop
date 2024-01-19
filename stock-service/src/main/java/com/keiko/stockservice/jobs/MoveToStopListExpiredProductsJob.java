package com.keiko.stockservice.jobs;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.StopList;
import com.keiko.stockservice.entity.notification.ProductStockEmail;
import com.keiko.stockservice.properties.EmailProperties;
import com.keiko.stockservice.service.NotificationService;
import com.keiko.stockservice.service.ProductStockService;
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
            ProductStockEmail data = ProductStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Move products stock to Stop list")
                    .message ("Stock next products moved to Stop list, because they're expired.")
                    .productStocks (productStocks)
                    .build ();
            notificationService.sendProductStocks (data);
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
