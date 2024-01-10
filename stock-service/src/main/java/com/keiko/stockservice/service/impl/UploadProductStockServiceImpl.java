package com.keiko.stockservice.service.impl;

import com.keiko.stockservice.entity.ProductStock;
import com.keiko.stockservice.properties.MailProperties;
import com.keiko.stockservice.service.ProductService;
import com.keiko.stockservice.service.ProductStockService;
import com.keiko.stockservice.service.UploadProductStockService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class UploadProductStockServiceImpl
        implements UploadProductStockService {

    @Autowired
    private ProductStockService productStockService;

    @Autowired
    private ProductService productService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void upload (MultipartFile file) {
        List<ProductStock> uploadData = uploadData (file);
        verifyData (uploadData);
    }

    private List<ProductStock> uploadData (MultipartFile file) {
        List<ProductStock> productStocks = new ArrayList<> ();
        try (InputStream is = file.getInputStream ()) {
            XSSFWorkbook workbook = new XSSFWorkbook (is);
            XSSFSheet worksheet = workbook.getSheetAt (0);

            for (int index = 1; index < worksheet.getPhysicalNumberOfRows (); index++) {
                ProductStock productStock = new ProductStock ();

                XSSFRow row = worksheet.getRow (index);
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

    private void verifyData (List<ProductStock> uploadData) {

        List<ProductStock> notExistProducts = new ArrayList<> ();
        for (ProductStock productStock : uploadData) {
            if (isExistsProduct (productStock.getEan ())) {
                productStockService.save (productStock);
            } else {
                notExistProducts.add (productStock);
            }
        }
        if (!notExistProducts.isEmpty ()) {
            Workbook workbook = transferToExcel (notExistProducts);
            sendNotification (workbook);
        }
    }

    private boolean isExistsProduct (String ean) {
        try {
            productService.findByEan (ean);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private Workbook transferToExcel (List<ProductStock> notExistProducts) {
        Workbook workbook = new XSSFWorkbook ();

        Sheet sheet = workbook.createSheet ("ProductStock");
        sheet.setColumnWidth (0, 4000);
        sheet.setColumnWidth (1, 4000);

        Row header = sheet.createRow (0);

        Cell headerCell = header.createCell (0);
        headerCell.setCellValue ("ean");
        headerCell = header.createCell (1);
        headerCell.setCellValue ("balance");

        for (ProductStock productStock : notExistProducts) {
            int i = 2;
            Row row = sheet.createRow (i);

            Cell cell = row.createCell (0);
            cell.setCellValue (productStock.getEan ());

            cell = row.createCell (1);
            cell.setCellValue (productStock.getBalance ());
            i++;
        }
        return workbook;
    }

    private void sendNotification (Workbook workbook) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage ();
        InputStream is = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream ()) {
            MimeMessageHelper messageHelper = new MimeMessageHelper (mimeMessage, true);
            messageHelper.setTo (mailProperties.getAdminEmail ());
            messageHelper.setFrom (mailProperties.getSupportEmail ());
            messageHelper.setSubject ("Upload Products stock wasn't completely");
            messageHelper.setText ("Stocks next products didn't upload, because ean product not found.");

            workbook.write (bos);
            byte[] barray = bos.toByteArray ();
            is = new ByteArrayInputStream (barray);
            messageHelper.addAttachment (
                    "Not exist products",
                    new ByteArrayResource (IOUtils.toByteArray (is)));

        } catch (MessagingException | IOException ex) {
            log.error (ex.getMessage ());
        } finally {

            // TODO
            try {
                is.close ();
            } catch (IOException ex) {
                log.error (ex.getMessage ());
            }
        }
        javaMailSender.send (mimeMessage);
    }
}
