package com.keiko.paymentservice.service.impl;

import com.keiko.paymentservice.entity.CompletedOrder;
import com.keiko.paymentservice.entity.PayerDetails;
import com.keiko.paymentservice.entity.PaymentOrder;
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
            log.error (ex.getMessage ());
            String status = String.format ("Error: %s", ex.getMessage ());
            return PaymentOrder.builder ()
                    .status (status)
                    .build ();
        }
    }

    @Override
    public CompletedOrder completePayment (String payId) {
        OrdersCaptureRequest ordersCaptureRequest = new OrdersCaptureRequest (payId);
        String message = null;
        try {
            HttpResponse<Order> httpResponse = payPalHttpClient.execute (ordersCaptureRequest);
            Order order = httpResponse.result ();
            if ("COMPLETED".equals (order.status ())) {
                message = String.format ("Payment complete, payId: %s", payId);
                return new CompletedOrder (order.status (), message);
            }
        } catch (IOException ex) {
            log.error (ex.getMessage ());
            message = ex.getMessage ();
        }
        return new CompletedOrder ("Error", message);
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
            message = String.format ("Error: %s", ex.getMessage ());
            log.error (message);
        }
        return PaymentOrder.builder ()
                .status (message)
                .build ();
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
