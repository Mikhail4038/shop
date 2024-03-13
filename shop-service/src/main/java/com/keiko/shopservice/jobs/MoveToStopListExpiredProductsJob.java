package com.keiko.shopservice.jobs;

import com.keiko.commonservice.entity.resource.stock.ProductStockData;
import com.keiko.commonservice.entity.resource.stock.ProductStockEmail;
import com.keiko.commonservice.service.DefaultCrudService;
import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.entity.StopList;
import com.keiko.shopservice.properties.EmailProperties;
import com.keiko.shopservice.service.ProductStocksService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.stream.Collectors.toList;

@Component
@Log4j2
public class MoveToStopListExpiredProductsJob {

    @Autowired
    private DefaultCrudService<Shop> shopService;

    @Autowired
    private ProductStocksService productStocksService;

    @Autowired
    private KafkaTemplate<Long, ProductStockEmail> kafkaTemplate;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailProperties emailProperties;

    @Scheduled (cron = "0 0 0 * * *")
    public void execute () {
        Callable callable = () -> {
            List<Shop> shops = shopService.fetchAll ();
            List<ProductStock> productStocks = new ArrayList<> ();
            for (Shop shop : shops) {
                Long shopId = shop.getId ();
                List<ProductStock> expiredProductStocks = findExpiredProductStocks (shopId);
                addToStopList (expiredProductStocks);
                productStocks.addAll (expiredProductStocks);
            }
            return productStocks;
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor ();
        Future<List<ProductStock>> future = executorService.submit (callable);

        try {
            List<ProductStock> productStocks = future.get ();
            if (!productStocks.isEmpty ()) {
                ProductStockEmail productStockEmail = ProductStockEmail.builder ()
                        .toAddress (emailProperties.getAdminEmail ())
                        .subject ("Move products stock to Stop list")
                        .message ("Stock next products moved to Stop list, because they're expired.")
                        .productStocks (convertToData (productStocks))
                        .build ();
                kafkaTemplate.send ("productStocks", productStockEmail);
            }
        } catch (InterruptedException ex) {
            log.error (ex.getMessage ());
        } catch (ExecutionException ex) {
            log.error (ex.getMessage ());
        }
    }

    private List<ProductStock> findExpiredProductStocks (Long shopId) {
        return productStocksService.findProductStocksToMoveExpiredStopList (shopId);
    }

    private void addToStopList (List<ProductStock> expiredProductStocks) {
        expiredProductStocks.forEach (stock -> stock.setStopList (StopList.EXPIRED));
        productStocksService.saveAll (expiredProductStocks);
    }

    private List<ProductStockData> convertToData (List<ProductStock> productStocks) {
        return productStocks.stream ()
                .map (stock -> modelMapper.map (stock, ProductStockData.class))
                .collect (toList ());
    }
}
