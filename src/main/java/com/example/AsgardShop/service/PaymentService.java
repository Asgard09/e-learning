package com.example.AsgardShop.service;

import com.example.AsgardShop.dto.request.PaymentInfoDTO;
import com.example.AsgardShop.exception.ApiRequestException;
import com.example.AsgardShop.model.Payment;
import com.example.AsgardShop.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, @Value("${stripe.secret.key}") String secretKey) {
        this.paymentRepository = paymentRepository;
        Stripe.apiKey = secretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentInfoDTO paymentInfoDTO) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentInfoDTO.getAmount());
        params.put("currency", paymentInfoDTO.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }

    public ResponseEntity<String> stripePayment(String userEmail) throws Exception{
        Payment payment = paymentRepository.findByUserEmail(userEmail);

        if (payment == null) {
            throw new ApiRequestException("Payment not found");
        }
        payment.setAmount(00.00);
        paymentRepository.save(payment);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
