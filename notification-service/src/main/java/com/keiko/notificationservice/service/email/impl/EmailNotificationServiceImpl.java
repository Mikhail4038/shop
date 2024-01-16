package com.keiko.notificationservice.service.email.impl;

import com.keiko.notificationservice.entity.ProductStock;
import com.keiko.notificationservice.entity.ProductsStockEmail;
import com.keiko.notificationservice.entity.SimpleEmail;
import com.keiko.notificationservice.properties.EmailProperties;
import com.keiko.notificationservice.service.email.EmailNotificationService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class EmailNotificationServiceImpl
        implements EmailNotificationService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration configuration;

    @Override
    public void sendEmail (SimpleEmail simpleEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage ();
        simpleMailMessage.setTo (simpleEmail.getToAddress ());
        simpleMailMessage.setFrom (emailProperties.getSupportEmail ());
        simpleMailMessage.setSubject (simpleEmail.getSubject ());
        simpleMailMessage.setText (simpleEmail.getMessage ());
        javaMailSender.send (simpleMailMessage);
    }


    @Override
    public void sendProductsStock (ProductsStockEmail productsStockEmail) {
        List<ProductStock> productsStock = productsStockEmail.getProductsStock ();
        MimeMessage mimeMessage = javaMailSender.createMimeMessage ();
        MimeMessageHelper helper = new MimeMessageHelper (mimeMessage);
        try {
            helper.setSubject (productsStockEmail.getSubject ());
            helper.setTo (productsStockEmail.getToAddress ());
            helper.setFrom (emailProperties.getSupportEmail ());
            String emailContent = getEmailContent ("productsStock.ftlh", productsStock, productsStockEmail.getMessage ());
            helper.setText (emailContent, true);
        } catch (MessagingException ex) {
            log.error (ex.getMessage ());
        }
        javaMailSender.send (mimeMessage);
    }

    private String getEmailContent (String template, List<ProductStock> productsStock, String message) {
        StringWriter stringWriter = new StringWriter ();
        Map<String, Object> model = new HashMap<> ();
        model.put ("productsStock", productsStock);
        model.put ("message", message);
        try {
            configuration.getTemplate (template).process (model, stringWriter);
        } catch (TemplateException ex) {
            log.error (ex.getMessage ());
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return stringWriter.getBuffer ().toString ();
    }
}
