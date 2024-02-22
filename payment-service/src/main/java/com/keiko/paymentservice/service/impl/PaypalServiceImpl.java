package com.keiko.paymentservice.service.impl;

import com.keiko.commonservice.entity.resource.payment.CompletedOrder;
import com.keiko.paymentservice.entity.PayerDetails;
import com.keiko.paymentservice.entity.PaymentOrder;
import com.keiko.paymentservice.exception.model.CompletedOrderException;
import com.keiko.paymentservice.exception.model.PaymentOrderException;
import com.keiko.paymentservice.service.PaypalService;
import com.paypal.core.PayPalHttpClient;
import com.paypal.http.HttpResponse;
import com.paypal.orders.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class PaypalServiceImpl implements PaypalService {

    @Autowired
    private PayPalHttpClient payPalHttpClient;

    @Override
    public PaymentOrder createPayment (BigDecimal sum) {
        String paymentIntent = "CAPTURE";
        String currencyCode = "USD";

        OrderRequest orderRequest = new OrderRequest ();
        orderRequest.checkoutPaymentIntent (paymentIntent);

        AmountWithBreakdown amountBreakdown
                = new AmountWithBreakdown ().currencyCode (currencyCode).value (sum.toString ());
        PurchaseUnitRequest purchaseUnitRequest
                = new PurchaseUnitRequest ().amountWithBreakdown (amountBreakdown);
        orderRequest.purchaseUnits (List.of (purchaseUnitRequest));

        ApplicationContext applicationContext = new ApplicationContext ()
                .returnUrl ("https://localhost:****/capture")
                .cancelUrl ("https://localhost:****/cancel");
        orderRequest.applicationContext (applicationContext);
        OrdersCreateRequest ordersCreateRequest = new OrdersCreateRequest ().requestBody (orderRequest);

        try {
            HttpResponse<Order> orderHttpResponse = payPalHttpClient.execute (ordersCreateRequest);
            Order order = orderHttpResponse.result ();

            String approveUrl = getApproveUrl (order);

            return PaymentOrder.builder ()
                    .status (order.status ())
                    .payId (order.id ())
                    .approveUrl (approveUrl)
                    .build ();
        } catch (IOException ex) {
            String message = ex.getMessage ();
            log.error (message);
            throw new PaymentOrderException (message);
        }
    }

    @Override
    public CompletedOrder completePayment (String payId) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest (payId);
        String message = null;
        String status = null;
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute (ordersCaptureRequest);
            Order order = httpResponse.result ();
            if ("COMPLETED".equals (order.status ())) {
                message = String.format ("Payment complete, payId: %s", payId);
                status = order.status ();
            } else {
                String warning = String.format ("Order status is: %s, must be completed", order.status ());
                log.warn (warning);
            }
        } catch (IOException ex) {
            message = ex.getMessage ();
            log.error (message);
            throw new CompletedOrderException (message);
        }
        return new CompletedOrder (status, message);
    }

    @Override
    public PaymentOrder getOrderDetails (String payId) {
        OrdersGetRequest ordersGetRequest = new OrdersGetRequest (payId);
        String message = null;
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute (ordersGetRequest);
            Order order = httpResponse.result ();
            PaymentOrder paymentOrder = PaymentOrder.builder ()
                    .status (order.status ())
                    .payId (order.id ())
                    .approveUrl (getApproveUrl (order))
                    .payerDetails (getPayerDetails (order))
                    .build ();
            return paymentOrder;
        } catch (IOException ex) {
            message = ex.getMessage ();
            log.error (message);
            throw new PaymentOrderException (message);
        }
    }

    private String getApproveUrl (Order order) {
        Optional<LinkDescription> approveUrl = order.links ().stream ()
                .filter (link -> "approve".equals (link.rel ()))
                .findFirst ();
        return approveUrl.map (LinkDescription::href).orElse (null);
    }

    private PayerDetails getPayerDetails (Order order) {
        Payer payer = order.payer ();
        PayerDetails payerDetails = null;
        if (nonNull (payer)) {
            payerDetails = PayerDetails.builder ()
                    .id (payer.payerId ())
                    .name (payer.name ().givenName ())
                    .email (payer.email ())
                    .build ();
        }
        return payerDetails;
    }
}
