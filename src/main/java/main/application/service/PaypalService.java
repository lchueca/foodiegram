package main.application.service;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import main.paypal.PaymentDetails;


public interface PaypalService {

    String authorizePayment(PaymentDetails payment) throws PayPalRESTException;
    Payment getPaymentDetails(String paymentId) throws PayPalRESTException;
    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;
}
