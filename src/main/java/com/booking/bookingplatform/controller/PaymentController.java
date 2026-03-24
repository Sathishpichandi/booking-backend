package com.booking.bookingplatform.controller;

import com.booking.bookingplatform.entity.Payment;
import com.booking.bookingplatform.entity.PaymentMethod;
import com.booking.bookingplatform.entity.PaymentReferenceType;
import com.booking.bookingplatform.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public Payment pay(@RequestParam Long referenceId,
                       @RequestParam PaymentReferenceType type,
                       @RequestParam BigDecimal amount,
                       @RequestParam PaymentMethod method) {

        return paymentService.makePayment(referenceId, type, amount, method);
    }
}
