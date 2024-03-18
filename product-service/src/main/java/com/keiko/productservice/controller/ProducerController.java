package com.keiko.productservice.controller;

import com.keiko.commonservice.controller.DefaultCrudController;
import com.keiko.productservice.dto.model.producer.ProducerDto;
import com.keiko.productservice.entity.Producer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.keiko.productservice.constants.WebResourceKeyConstants.PRODUCER_BASE;

@RestController
@RequestMapping (value = PRODUCER_BASE)
public class ProducerController extends DefaultCrudController<Producer, ProducerDto> {
}
