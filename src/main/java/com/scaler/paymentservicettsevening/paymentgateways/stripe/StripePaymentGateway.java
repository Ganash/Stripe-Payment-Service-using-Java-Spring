package com.scaler.paymentservicettsevening.paymentgateways.stripe;

import com.scaler.paymentservicettsevening.paymentgateways.PaymentGateway;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripePaymentGateway implements PaymentGateway {

    @Value("${stripe.secret_key}")
    private String stripeSecretKey;

    @Override
    public String generatePaymentLink(Long amount) throws StripeException {


        // Setup Product Catalog

        // In Stripe, you first create a price

        // First stripe asks you to create a price object. For Example if you have to take the payment of 100rs, you have to create an object of price datatype

        Stripe.apiKey = stripeSecretKey;

        ProductCreateParams params =
                ProductCreateParams.builder().setName("GENERIC").build();
        Product product = Product.create(params);

        PriceCreateParams priceCreateParams =
                PriceCreateParams.builder()
                        .setCurrency("inr")
                        .setUnitAmount(amount) // amount * 100 = Rs 10 => 1000
                        .setProduct(product.getId())
                        .build();

        Price price = Price.create(priceCreateParams);

        // Create a Payment Link

        // After creating a price, you will create a paymentLink for that much amount of money.
        // that's how stripe works

        // then this price id I will pass when I am creating an object of paymentLink.

        // we do able to handle floats that doesn't mean we will store floats as well. In UI, it will look like floats but internally it will be stored as integers

        PaymentLinkCreateParams paymentLinkCreateParams =
                PaymentLinkCreateParams.builder()
                        .addLineItem
                                (
                                        PaymentLinkCreateParams.LineItem.builder()
                                                .setPrice(price.getId())
                                                .setQuantity(1L)
                                                .build()
                                )
                        .setAfterCompletion(
                                PaymentLinkCreateParams.AfterCompletion.builder()
                                        .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                                        .setRedirect(
                                                PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                                        .setUrl("https://platform.bosscoderacademy.com/login").build()
                                        ).build()
                        )
                        .build();

        PaymentLink paymentLink = PaymentLink.create(paymentLinkCreateParams);

        return paymentLink.getUrl();
    }
}
