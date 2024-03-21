package com.keiko.orderservice.controller;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.orderservice.entity.resources.PaymentOrder;
import com.keiko.orderservice.service.PaymentService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.keiko.commonservice.constants.WebResourceKeyConstants.*;
import static com.keiko.orderservice.util.TestData.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest (PaypalController.class)
public class PaypalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private static PaymentOrder paymentOrder;
    private static CompletedOrder completedOrder;

    @BeforeAll
    static void setUp () {
        paymentOrder = testPaymentOrder ();
        completedOrder = testCompletedOrder ();
    }

    @Test
    void createPayment_should_successfully () throws Exception {
        when (paymentService.createPayment (ORDER_ID)).thenReturn (paymentOrder);

        mockMvc.perform (post (PAYPAL_BASE + CREATE_PAYMENT)
                .contentType (APPLICATION_JSON_VALUE)
                .queryParam ("orderId", ORDER_ID.toString ()))
                .andExpect (jsonPath ("$.status", is (paymentOrder.getStatus ())))
                .andExpect (jsonPath ("$.payId", is (paymentOrder.getPayId ())))
                .andExpect (jsonPath ("$.approveUrl", is (paymentOrder.getApproveUrl ())))
                .andExpect (status ().isOk ());

        verify (paymentService, times (1)).createPayment (anyLong ());
    }

    @Test
    void completePayment_should_successfully () throws Exception {
        when (paymentService.completePayment (PAY_ID)).thenReturn (completedOrder);

        mockMvc.perform (post (PAYPAL_BASE + COMPLETE_PAYMENT)
                .contentType (APPLICATION_JSON_VALUE)
                .queryParam ("payId", PAY_ID.toString ()))
                .andExpect (jsonPath ("$.status", is (completedOrder.getStatus ())))
                .andExpect (jsonPath ("$.message", is (completedOrder.getMessage ())))
                .andExpect (status ().isOk ());

        verify (paymentService, times (1)).completePayment (anyString ());
    }

}
