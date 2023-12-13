package com.scaler.paymentservicettsevening.controllers;

import com.scaler.paymentservicettsevening.dtos.CreatePaymentLinkRequestDto;
import com.scaler.paymentservicettsevening.services.PaymentService;
import com.stripe.exception.StripeException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController (PaymentService paymentService){

        this.paymentService = paymentService;
    }

    // createPaymentLink expects CreatePaymentLinkRequestDto
    // we will get CreatePaymentLinkRequestDto in @RequestBody

    @PostMapping("/")
    public String createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request) throws StripeException {

        return paymentService.createPaymentLink(request.getOrderId());
    }
}
