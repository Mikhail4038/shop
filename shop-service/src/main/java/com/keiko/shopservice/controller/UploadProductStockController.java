package com.keiko.shopservice.controller;

import com.keiko.shopservice.service.UploadProductStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.keiko.shopservice.constants.WebResourceKeyConstants.PRODUCT_STOCK_BASE;
import static com.keiko.shopservice.constants.WebResourceKeyConstants.UPLOAD;

@RestController
@RequestMapping (value = PRODUCT_STOCK_BASE)
public class UploadProductStockController {

    @Autowired
    private UploadProductStockService uploadProductStockService;

    @PostMapping (value = UPLOAD)
    public ResponseEntity uploading (@RequestParam MultipartFile file,
                                     @RequestParam Long shopId) {
        uploadProductStockService.upload (file, shopId);
        return ResponseEntity.ok ().build ();
    }
}
