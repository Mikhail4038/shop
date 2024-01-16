package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.entity.ProductsStockEmail;
import com.keiko.stockservice.properties.EmailProperties;
import com.keiko.stockservice.service.NotificationService;
import com.keiko.stockservice.service.ProductService;
import com.keiko.stockservice.service.ProductStockService;
import com.keiko.stockservice.service.UploadProductStockService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Log4j2
public class UploadProductStockServiceImpl
        implements UploadProductStockService {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void upload (MultipartFile file) {
        List<ProductStock> productStocks = uploadData (file);
        verifyData (productStocks);
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

                productStock.setEan (ean.toString ());
                productStock.setBalance (balance);
                productStocks.add (productStock);
            }
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return productStocks;
    }

    private void verifyData (List<ProductStock> productStocks) {
        List<ProductStock> notExistProducts = new ArrayList<> ();
        List<ProductStock> toSaveProductsStock = new ArrayList<> ();

        for (ProductStock productStock : productStocks) {

            if (isExistsProduct (productStock.getEan ())) {
                toSaveProductsStock.add (productStock);
                addProductStock (productStock);
            } else {
                notExistProducts.add (productStock);
            }
        }

        if (!toSaveProductsStock.isEmpty ()) {
            ProductsStockEmail data = ProductsStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Upload Products stock was completely")
                    .message ("Stock next products uploaded successful.")
                    .productsStock (toSaveProductsStock)
                    .build ();
            sendNotification (data);
        }

        if (!notExistProducts.isEmpty ()) {
            ProductsStockEmail data = ProductsStockEmail.builder ()
                    .toAddress (emailProperties.getAdminEmail ())
                    .subject ("Upload Products stock wasn't completely")
                    .message ("Stock next products didn't upload, because ean product not found.")
                    .productsStock (notExistProducts)
                    .build ();
            sendNotification (data);
        }
    }

    private void addProductStock (ProductStock productStock) {
        try {
            ProductStock savedProductStock = productStockService.fetchByEan (productStock.getEan ());
            Double balance = savedProductStock.getBalance ();
            balance += productStock.getBalance ();
            savedProductStock.setBalance (balance);
            productStockService.save (savedProductStock);
        } catch (NoSuchElementException ex) {
            productStockService.save (productStock);
        }
    }

    private boolean isExistsProduct (String ean) {
        return productService.isExist (ean);
    }

    private void sendNotification (ProductsStockEmail productsStockEmail) {
        notificationService.sendProductsStock (productsStockEmail);
    }
}
