package com.keiko.stockservice.jobs;

import com.keiko.stockservice.service.ProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ImportProductStockJob {

    @Autowired
    private ProductStockService productStockService;

    //@Scheduled (cron = "0 */1 * * * *")
    public void importData () throws IOException {
        uploadData ();
        verifyData ();
        deleteFile ();
    }

    private void uploadData () throws IOException {
        File dir = new File ("src/main/resources/import");
        for (File file : dir.listFiles ()) {
            file.delete ();
        }

       /* FileInputStream fis = new FileInputStream (file);
        XSSFWorkbook workbook = new XSSFWorkbook (fis);
        XSSFSheet worksheet = workbook.getSheetAt (0);

        for (int index = 1; index < worksheet.getPhysicalNumberOfRows (); index++) {
            ProductStock productStock = new ProductStock ();

            XSSFRow row = worksheet.getRow (index);
            Integer ean = (int) row.getCell (0).getNumericCellValue ();
            Double balance = row.getCell (1).getNumericCellValue ();

            productStock.setEan (ean.toString ());
            productStock.setBalance (balance);
            productStockService.save (productStock);
        }
        fis.close ();*/
        /*boolean result = Files.deleteIfExists (file.toPath ());
        System.out.println (result);*/
    }

    private void verifyData () {
    }

    private void deleteFile () {
    }

    public static void main (String[] args) {

        Resource resource = new ClassPathResource ("import/zxc.txt");
        try {
            File file = resource.getFile ();
            System.out.println (file.exists ());
            file.delete ();
        } catch (IOException e) {
            e.printStackTrace ();
        }

        /*File dir = new File ("src/main/resources/import");
        for (File file : dir.listFiles ()) {
            file.delete ();
        }*/
    }
}
