package com.ecom.PaymentService.controller;

import com.ecom.PaymentService.dto.*;
import com.ecom.PaymentService.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponse> initiatePayment(@Valid @RequestBody PaymentRequest paymentRequest){
        return ResponseEntity.ok( paymentService.createPayment(paymentRequest.orderId(),paymentRequest.totalAmount()));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ConfirmPaymentResponse> initiatePayment(@Valid @RequestBody ConfirmPaymentRequest paymentRequest){
        return ResponseEntity.ok( paymentService.confirmPayment(paymentRequest.paymentId(),paymentRequest.status()));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment(@PathVariable UUID paymentId){
        return ResponseEntity.ok(paymentService.verifyPayment(paymentId));
    }
}
