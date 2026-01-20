package com.ecom.PaymentService.service;

import com.ecom.PaymentService.dto.ConfirmPaymentResponse;
import com.ecom.PaymentService.dto.PaymentResponse;
import com.ecom.PaymentService.dto.VerifyPaymentResponse;
import com.ecom.PaymentService.entity.Payment;
import com.ecom.PaymentService.entity.PaymentStatus;
import com.ecom.PaymentService.producer.PaymentEventProducer;
import com.ecom.PaymentService.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentEventProducer paymentEventProducer;

    @Transactional
    public PaymentResponse createPayment(UUID orderId, double amount){
        Payment payment = Payment.builder()
                .orderId(orderId)
                .amount(amount)
                .status(PaymentStatus.INITIATED)
                .createdAt(LocalDateTime.now())
                .build();

        Payment savedPayment = paymentRepository.save(payment);
        String rediretUrl =
                "http://localhost:5173/payment-gateway"
                        + "?paymentId=" + payment.getId()
                        + "&orderId=" + payment.getOrderId()
                        + "&amount=" + payment.getAmount()
                        + "&callbackUrl=http://localhost:5173/payment-callback";
        return PaymentResponse.builder()
                    .paymentId(savedPayment.getId())
                    .redirectUrl(rediretUrl)
                    .build();


    }


//    dummy gateway uses this to update the payment status
    public ConfirmPaymentResponse confirmPayment(UUID paymentId,PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        if(status.equals(PaymentStatus.SUCCESS)){
            payment.setStatus(PaymentStatus.SUCCESS);
            paymentEventProducer.publishPaymentCompleted(payment.getOrderId(),payment.getId(),payment.getAmount());
        } else if (status.equals(PaymentStatus.FAILED)) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentEventProducer.publishPaymentFailed(payment.getOrderId(),payment.getId(),"technical error");
        }
        paymentRepository.save(payment);


        return ConfirmPaymentResponse.builder().message("payment status updated").build();
    }

//    this method is for frontend to verify the payment
    public VerifyPaymentResponse verifyPayment(UUID paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow();
        if(payment.getStatus().equals(PaymentStatus.SUCCESS)){
            return VerifyPaymentResponse.builder().status("SUCCESS").build();
        }
        else return VerifyPaymentResponse.builder().status("FAILED").build();
    }
}
