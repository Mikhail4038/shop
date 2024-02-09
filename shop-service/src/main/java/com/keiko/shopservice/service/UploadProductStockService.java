package com.keiko.shopservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadProductStockService {

    void upload (MultipartFile file, Long shopId);
}
