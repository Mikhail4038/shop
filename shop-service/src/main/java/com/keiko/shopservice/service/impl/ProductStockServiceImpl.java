package com.keiko.shopservice.service.impl;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.StopList;
import com.keiko.shopservice.entity.resources.OrderEntry;
import com.keiko.shopservice.exception.model.ProductStockLevelException;
import com.keiko.shopservice.repository.ProductStockRepository;
import com.keiko.shopservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static com.keiko.shopservice.repository.specs.ProductStockSpec.*;
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
    public void bookedStock (OrderEntry orderEntry) {
        String ean = orderEntry.getProductEan ();
        Long quantity = orderEntry.getQuantity ();

        List<ProductStock> productStocks =
                productStockRepository.findAll (byEan (ean).and (inStopList (StopList.NONE)));
        productStocks.sort (comparing (ProductStock::getExpirationDate));

        ProductStock stock;
        for (int i = 0; i < productStocks.size (); i++) {
            stock = productStocks.get (i);
            Long balance = stock.getBalance ();
            Long booked = stock.getBooked ();

            if (balance.equals (quantity)) {
                stock.setBalance (balance - quantity);
                stock.setBooked (booked + quantity);
                stock.setStopList (StopList.BOOKED);
                super.save (stock);
                break;
            }
            if (balance > quantity) {
                stock.setBalance (balance - quantity);
                stock.setBooked (booked + quantity);
                super.save (stock);
                break;
            } else {
                stock.setBalance (0L);
                stock.setBooked (booked + balance);
                stock.setStopList (StopList.BOOKED);
                quantity -= balance;
            }
        }
    }

    @Override
    public void cancelBookedStock (OrderEntry orderEntry) {
        String ean = orderEntry.getProductEan ();
        Long quantity = orderEntry.getQuantity ();

        List<ProductStock> productStocks =
                productStockRepository.findAll (byEan (ean).and (hasBookedStock ()));
        productStocks.sort (comparing (ProductStock::getExpirationDate).reversed ());

        ProductStock stock;
        for (int i = 0; i < productStocks.size (); i++) {
            stock = productStocks.get (i);
            Long booked = stock.getBooked ();
            Long balance = stock.getBalance ();

            if (booked.equals (quantity)) {
                stock.setBooked (0L);
                stock.setBalance (balance + quantity);
                checkStopListStatus (stock);
                super.save (stock);
                break;
            }

            if (booked > quantity) {
                stock.setBooked (booked - quantity);
                stock.setBalance (balance + quantity);
                checkStopListStatus (stock);
                super.save (stock);
                break;
            } else {
                stock.setBooked (0L);
                stock.setBalance (booked + balance);
                checkStopListStatus (stock);
                super.save (stock);
                quantity -= booked;
            }
        }
    }

    @Override
    public void sellStock (List<OrderEntry> entries) {
        for (OrderEntry entry : entries) {
            String ean = entry.getProductEan ();
            Long quantity = entry.getQuantity ();

            List<ProductStock> productStocks =
                    productStockRepository.findAll (byEan (ean).and (hasBookedStock ()));
            productStocks.sort (comparing (ProductStock::getExpirationDate));

            ProductStock stock;
            for (int i = 0; i < productStocks.size (); i++) {
                stock = productStocks.get (i);
                Long booked = stock.getBooked ();

                if (booked.equals (quantity)) {
                    stock.setBooked (0L);
                    checkStockBalance (stock);
                    super.save (stock);
                    break;
                }

                if (booked > quantity) {
                    stock.setBooked (booked - quantity);
                    super.save (stock);
                    break;
                } else {
                    stock.setBooked (0L);
                    checkStockBalance (stock);
                    super.save (stock);
                    quantity -= booked;
                }
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

    private void checkStopListStatus (ProductStock stock) {
        if (!stock.getStopList ().equals (StopList.NONE)) {
            stock.setStopList (StopList.NONE);
        }
    }

    private void checkStockBalance (ProductStock stock) {
        if (stock.getBalance ().equals (0L)) {
            stock.setStopList (StopList.SOLD);
        }
    }
}
