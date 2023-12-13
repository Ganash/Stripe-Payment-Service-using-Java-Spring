package com.scaler.paymentservicettsevening.services;

import com.scaler.paymentservicettsevening.paymentgateways.stripe.StripePaymentGateway;
import com.stripe.exception.StripeException;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private StripePaymentGateway stripePaymentGateway;

    public PaymentService (StripePaymentGateway stripePaymentGateway) {

        this.stripePaymentGateway = stripePaymentGateway;
    }
    public String createPaymentLink(Long orderId) throws StripeException {
        // we are generating payment Link of 100 Rs => 100 * 100 => 10000L
        return stripePaymentGateway.generatePaymentLink(10000L);
    }
}
