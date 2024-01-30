package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.StopList;
import com.keiko.stockservice.exception.model.ProductStockLevelException;
import com.keiko.stockservice.repository.ProductStockRepository;
import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.keiko.stockservice.repository.specs.ProductStockSpec.*;
import static java.util.Comparator.comparing;

@Service
public class ProductStockServiceImpl extends AbstractCrudServiceImpl<ProductStock>
        implements ProductStockService {

    @Autowired
    private ProductStockRepository productStockRepository;

    @Override
    public List<ProductStock> fetchByEan (String ean) {
        return productStockRepository.findByEan (ean);
    }

    @Override
    public Long countProductStockForSell (String ean) {
        List<ProductStock> productStocks = productStockRepository.findAll (byEan (ean).and (inStopList (StopList.NONE)));
        Long availableStock =
                productStocks.stream ()
                        .mapToLong (ProductStock::getBalance)
                        .summaryStatistics ().getSum ();

        if (availableStock.equals (0L)) {
            String message = String.format ("Product with ean: %s out of stock.", ean);
            throw new ProductStockLevelException (message);
        }
        return availableStock;
    }

    @Override
    public void reduceStockLevel (String ean, Long value) {
        List<ProductStock> productStocks = productStockRepository.findAll (byEan (ean).and (inStopList (StopList.NONE)));
        productStocks.sort (comparing (ProductStock::getExpirationDate));

        for (int i = 0; i < productStocks.size (); i++) {
            ProductStock productStock = productStocks.get (i);
            Long balance = productStock.getBalance ();

            if (balance.equals (value)) {
                soldOutProductStocks (productStock);
                break;
            }

            if (balance < value) {
                soldOutProductStocks (productStock);
                value -= balance;
            } else {
                Long diff = balance - value;
                productStock.setBalance (diff);
                productStockRepository.save (productStock);
                break;
            }
        }
    }

    @Override
    public List<ProductStock> findProductStocksToMoveExpiredStopList () {
        return productStockRepository.findAll (inStopList (StopList.NONE).and (isExpired ()));
    }

    @Override
    public void saveAll (Collection<ProductStock> productStocks) {
        productStockRepository.saveAll (productStocks);
    }

    private void soldOutProductStocks (ProductStock productStock) {
        productStock.setBalance (0L);
        productStock.setStopList (StopList.SOLD);
        productStockRepository.save (productStock);
    }
}
