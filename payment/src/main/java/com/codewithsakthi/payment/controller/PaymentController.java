package com.codewithsakthi.payment.controller;

import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    @GetMapping("/deduct")
    public ResponseEntity<String> deductPayment() {
        logger.info("processing deduction...");
        return ResponseEntity.internalServerError().body("Deduct Failed");
    }

    @GetMapping("/refund")
    public ResponseEntity<String> processRefund() {
        logger.info("Refunding payment...");
        return ResponseEntity.ok("Refunded");
    }

    
}

