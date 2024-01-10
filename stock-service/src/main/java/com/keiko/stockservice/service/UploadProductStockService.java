package com.keiko.stockservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadProductStockService {

    void upload (MultipartFile file);
}
