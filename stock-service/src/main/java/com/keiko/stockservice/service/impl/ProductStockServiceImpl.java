package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.StopList;
import com.keiko.stockservice.exception.model.ProductStockLevelException;
import com.keiko.stockservice.repository.ProductStockRepository;
import com.keiko.stockservice.request.BookingStockRequest;
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
        List<ProductStock> productStocks =
                productStockRepository.findAll (byEan (ean).and (inStopList (StopList.NONE)));
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
    public void bookedStock (BookingStockRequest request) {
        String ean = request.getEan ();
        Long value = request.getValue ();

        List<ProductStock> productStocks =
                productStockRepository.findAll (byEan (ean).and (inStopList (StopList.NONE)));
        productStocks.sort (comparing (ProductStock::getExpirationDate));

        ProductStock stock;
        for (int i = 0; i < productStocks.size (); i++) {
            stock = productStocks.get (i);
            Long balance = stock.getBalance ();
            Long booked = stock.getBooked ();

            if (balance.equals (value)) {
                stock.setBalance (balance - value);
                stock.setBooked (booked + value);
                stock.setStopList (StopList.BOOKED);
                super.save (stock);
                break;
            }
            if (balance > value) {
                stock.setBalance (balance - value);
                stock.setBooked (booked + value);
                super.save (stock);
                break;
            } else {
                stock.setBalance (0L);
                stock.setBooked (booked + balance);
                stock.setStopList (StopList.BOOKED);
                value -= balance;
            }
        }
    }

    @Override
    public void cancelBookedStock (BookingStockRequest request) {
        String ean = request.getEan ();
        Long value = request.getValue ();

        List<ProductStock> productStocks =
                productStockRepository.findAll (byEan (ean).and (hasBookedStock ()));
        productStocks.sort (comparing (ProductStock::getExpirationDate).reversed ());

        ProductStock stock;
        for (int i = 0; i < productStocks.size (); i++) {
            stock = productStocks.get (i);
            Long booked = stock.getBooked ();
            Long balance = stock.getBalance ();

            if (booked.equals (value)) {
                stock.setBooked (0L);
                stock.setBalance (balance + value);
                if (!stock.getStopList ().equals (StopList.NONE)) {
                    stock.setStopList (StopList.NONE);
                }
                super.save (stock);
                break;
            }

            if (booked > value) {
                stock.setBooked (booked - value);
                stock.setBalance (balance + value);
                if (!stock.getStopList ().equals (StopList.NONE)) {
                    stock.setStopList (StopList.NONE);
                }
                super.save (stock);
                break;
            } else {
                stock.setBooked (0L);
                stock.setBalance (booked + balance);
                if (!stock.getStopList ().equals (StopList.NONE)) {
                    stock.setStopList (StopList.NONE);
                }
                super.save (stock);
                value -= booked;
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

    private void bookedProductStocks (ProductStock productStock, Long value) {
        productStock.setBalance (0L);
        productStock.setBooked (value);
        productStock.setStopList (StopList.BOOKED);
        productStockRepository.save (productStock);
    }
}
