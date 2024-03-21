package com.keiko.shopservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadProductStockService {

    void uploadProductStocks (MultipartFile file, Long shopId);
}
