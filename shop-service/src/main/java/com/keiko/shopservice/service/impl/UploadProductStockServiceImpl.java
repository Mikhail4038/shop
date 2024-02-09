package com.keiko.shopservice.service.impl;

import com.keiko.shopservice.entity.ProductStock;
import com.keiko.shopservice.entity.Shop;
import com.keiko.shopservice.entity.StopList;
import com.keiko.shopservice.entity.resources.ProductStockData;
import com.keiko.shopservice.entity.resources.ProductStockEmail;
import com.keiko.shopservice.properties.EmailProperties;
import com.keiko.shopservice.service.AbstractCrudService;
import com.keiko.shopservice.service.UploadProductStockService;
import com.keiko.shopservice.service.resources.NotificationService;
import com.keiko.shopservice.service.resources.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@Log4j2
public class UploadProductStockServiceImpl
        implements UploadProductStockService {

    @Autowired
    private ShopServiceImpl shopService;

    @Autowired
    private AbstractCrudService<ProductStock> productStockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void upload (MultipartFile file, Long shopId) {
        Shop shop = shopService.fetchById (shopId);
        List<ProductStock> uploadProductStocks = uploadProductStocks (file, shop);
        saveProductStocks (uploadProductStocks, shop);
    }

    private List<ProductStock> uploadProductStocks (MultipartFile file, Shop shop) {
        List<ProductStock> productStocks = new ArrayList<> ();
        try (InputStream is = file.getInputStream ()) {
            XSSFWorkbook workbook = new XSSFWorkbook (is);
            XSSFSheet worksheet = workbook.getSheetAt (0);

            for (int index = 1; index < worksheet.getPhysicalNumberOfRows (); index++) {
                ProductStock productStock = new ProductStock ();

                XSSFRow row = worksheet.getRow (index);

                Cell cell = row.getCell (1);
                if (cell == null || cell.getCellType () == Cell.CELL_TYPE_BLANK) {
                    continue;
                }

                Integer ean = (int) row.getCell (0).getNumericCellValue ();
                Double balance = row.getCell (1).getNumericCellValue ();
                LocalDate expirationDate = row.getCell (2).getDateCellValue ()
                        .toInstant ().atZone (ZoneId.systemDefault ()).toLocalDate ();

                productStock.setEan (ean.toString ());
                productStock.setBalance (balance.longValue ());
                productStock.setExpirationDate (expirationDate);
                productStock.setShop (shop);
                productStocks.add (productStock);
            }
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return productStocks;
    }

    private void saveProductStocks (List<ProductStock> uploadProductStocks, Shop shop) {
        List<ProductStock> notExistProducts = new ArrayList<> ();
        List<ProductStock> existProducts = new ArrayList<> ();

        for (ProductStock stock : uploadProductStocks) {
            if (isExistsProduct (stock.getEan ())) {
                addProductStock (stock, shop);
                existProducts.add (stock);
            } else {
                notExistProducts.add (stock);
            }
        }

        if (!existProducts.isEmpty ()) {
            ProductStockEmail data = ProductStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Upload Products stock was completely")
                    .message ("Stock next products uploaded successful.")
                    .productStocks (convertToData (existProducts))
                    .build ();
            sendNotification (data);
        }

        if (!notExistProducts.isEmpty ()) {
            ProductStockEmail data = ProductStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Upload Products stock wasn't completely")
                    .message ("Stock next products didn't upload, because ean product not found.")
                    .productStocks (convertToData (notExistProducts))
                    .build ();
            sendNotification (data);
        }
    }

    private List<ProductStockData> convertToData (List<ProductStock> productStocks) {
        return productStocks.stream ()
                .map (stock -> modelMapper.map (stock, ProductStockData.class))
                .collect (toList ());
    }

    private void addProductStock (ProductStock productStock, Shop shop) {
        String ean = productStock.getEan ();
        List<ProductStock> productStocks
                = shopService.fetchStocksByEan (ean, shop);

        if (productStocks.isEmpty ()) {
            productStockService.save (productStock);
        } else {
            addStockByExpirationDate (productStocks, productStock);
        }
    }

    private boolean isExistsProduct (String ean) {
        return productService.isExist (ean);
    }

    private void addStockByExpirationDate (List<ProductStock> productStocks, ProductStock productStock) {

        Optional<ProductStock> theSameExpirationDate = productStocks.stream ()
                .filter (s -> s.getExpirationDate ().equals (productStock.getExpirationDate ()))
                .findFirst ();

        if (theSameExpirationDate.isPresent ()) {
            ProductStock stock = theSameExpirationDate.get ();
            Long balance = stock.getBalance ();
            balance += productStock.getBalance ();
            stock.setBalance (balance);
            if (!stock.getStopList ().equals (StopList.NONE)) {
                stock.setStopList (StopList.NONE);
            }
            productStockService.save (stock);
        } else {
            productStockService.save (productStock);
        }
    }

    private void sendNotification (ProductStockEmail productStockEmail) {
        notificationService.sendProductStocks (productStockEmail);
    }
}
