package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.resources.ProductStockEmail;
import com.keiko.stockservice.properties.EmailProperties;
import com.keiko.stockservice.service.UploadProductStockService;
import com.keiko.stockservice.service.resources.NotificationService;
import com.keiko.stockservice.service.resources.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

@Service
@Log4j2
public class UploadProductStockServiceImpl
        implements UploadProductStockService {

    @Autowired
    private ProductStockServiceImpl productStockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void upload (MultipartFile file) {
        List<ProductStock> uploadProductStocks = uploadData (file);
        verifyData (uploadProductStocks);
    }

    private List<ProductStock> uploadData (MultipartFile file) {
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
                productStocks.add (productStock);
            }
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return productStocks;
    }

    private void verifyData (List<ProductStock> uploadProductStocks) {
        List<ProductStock> notExistProducts = new ArrayList<> ();
        List<ProductStock> existProducts = new ArrayList<> ();


        for (ProductStock stock : uploadProductStocks) {
            if (isExistsProduct (stock.getEan ())) {
                addProductStock (stock);
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
                    .productStocks (existProducts)
                    .build ();
            sendNotification (data);
        }

        if (!notExistProducts.isEmpty ()) {
            ProductStockEmail data = ProductStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Upload Products stock wasn't completely")
                    .message ("Stock next products didn't upload, because ean product not found.")
                    .productStocks (notExistProducts)
                    .build ();
            sendNotification (data);
        }
    }

    private void addProductStock (ProductStock productStock) {
        List<ProductStock> productStocks
                = productStockService.fetchByEan (productStock.getEan ());

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
            productStockService.save (stock);
        } else {
            productStockService.save (productStock);
        }
    }

    private void sendNotification (ProductStockEmail productStockEmail) {
        notificationService.sendProductStocks (productStockEmail);
    }
}
