package com.keiko.stockservice.controller;

import com.keiko.stockservice.service.UploadProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.keiko.stockservice.constants.WebResourceKeyConstants.PRODUCT_STOCK_BASE;
import static com.keiko.stockservice.constants.WebResourceKeyConstants.UPLOAD;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class UploadProductStockController {

    @Autowired
    private UploadProductStockService uploadProductStockService;

    @PostMapping (value = UPLOAD)
    public ResponseEntity uploading (@RequestParam MultipartFile file) {
        uploadProductStockService.upload (file);
        return ResponseEntity.ok ().build ();
    }
}
