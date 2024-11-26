package com.codewithsakthi.inventory.fiegnclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "PAYMENT")
public interface PaymentClient {

    @GetMapping("/payment/deduct")
    public ResponseEntity deductPayment();
    
}
