package com.keiko.notificationservice.service.email.impl;

import com.keiko.notificationservice.entity.OrderDetailsEmail;
import com.keiko.notificationservice.entity.ProductStocksEmail;
import com.keiko.notificationservice.entity.SimpleEmail;
import com.keiko.notificationservice.entity.resources.Order;
import com.keiko.notificationservice.entity.resources.ProductStockData;
import com.keiko.notificationservice.properties.EmailProperties;
import com.keiko.notificationservice.service.email.EmailNotificationService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
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
    @KafkaListener (topics = {"confirmRegistration"}, groupId = "default-group")
    public void sendEmail (SimpleEmail simpleEmail) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage ();
        simpleMailMessage.setTo (simpleEmail.getToAddress ());
        simpleMailMessage.setFrom (emailProperties.getSupportEmail ());
        simpleMailMessage.setSubject (simpleEmail.getSubject ());
        simpleMailMessage.setText (simpleEmail.getMessage ());
        javaMailSender.send (simpleMailMessage);
        log.info (String.format ("Confirm registration email, sent to: %s", simpleEmail.getToAddress ()));
    }

    @Override
    @KafkaListener (topics = {"productStocks"}, groupId = "default-group")
    public void sendProductsStock (ProductStocksEmail productStocksEmail) {
        MimeMessageHelper helper = generateMimeMessageHelper (productStocksEmail);
        try {
            List<ProductStockData> productStocks = productStocksEmail.getProductStocks ();
            String message = productStocksEmail.getMessage ();
            String emailContent = getProductStocksEmailContent (productStocks, message);
            helper.setText (emailContent, true);
        } catch (MessagingException ex) {
            log.error (ex.getMessage ());
        }
        javaMailSender.send (helper.getMimeMessage ());
        log.info (String.format ("Product stocks email, sent to: %s", productStocksEmail.getToAddress ()));
    }

    @Override
    @KafkaListener (topics = {"orderDetails"}, groupId = "default-group")
    public void sendOrderDetails (OrderDetailsEmail orderDetailsEmail) {
        MimeMessageHelper helper = generateMimeMessageHelper (orderDetailsEmail);
        try {
            Order order = orderDetailsEmail.getOrder ();
            String message = orderDetailsEmail.getMessage ();
            String emailContent = getOrderDetailsEmailContent (order, message);
            helper.setText (emailContent, true);
        } catch (MessagingException ex) {
            log.error (ex.getMessage ());
        }
        javaMailSender.send (helper.getMimeMessage ());
        log.info (String.format ("Order details sent to: %s", orderDetailsEmail.getToAddress ()));
        log.info (String.format ("Order details email, sent to: %s", orderDetailsEmail.getToAddress ()));
    }

    private String getOrderDetailsEmailContent (Order order, String message) {
        StringWriter stringWriter = new StringWriter ();
        Map<String, Object> model = new HashMap<> ();
        model.put ("order", order);
        model.put ("message", message);
        String template = "orderDetails.ftlh";
        try {
            configuration.getTemplate (template).process (model, stringWriter);
        } catch (TemplateException ex) {
            log.error (ex.getMessage ());
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return stringWriter.getBuffer ().toString ();
    }

    private String getProductStocksEmailContent (List<ProductStockData> productStocks, String message) {
        StringWriter stringWriter = new StringWriter ();
        Map<String, Object> model = new HashMap<> ();
        model.put ("productStocks", productStocks);
        model.put ("message", message);
        String template = "productsStock.ftlh";
        try {
            configuration.getTemplate (template).process (model, stringWriter);
        } catch (TemplateException ex) {
            log.error (ex.getMessage ());
        } catch (IOException ex) {
            log.error (ex.getMessage ());
        }
        return stringWriter.getBuffer ().toString ();
    }

    private MimeMessageHelper generateMimeMessageHelper (SimpleEmail simpleEmail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage ();
        MimeMessageHelper helper = new MimeMessageHelper (mimeMessage);
        try {
            helper.setSubject (simpleEmail.getSubject ());
            helper.setTo (simpleEmail.getToAddress ());
            helper.setFrom (emailProperties.getSupportEmail ());
        } catch (MessagingException ex) {
            log.error (ex.getMessage ());
        }
        return helper;
    }
}
