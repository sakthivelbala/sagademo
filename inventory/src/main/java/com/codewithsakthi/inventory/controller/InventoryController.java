package com.codewithsakthi.inventory.controller;

import org.springframework.web.bind.annotation.RestController;

import com.codewithsakthi.inventory.fiegnclients.PaymentClient;
import com.netflix.discovery.converters.Auto;

import feign.FeignException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class InventoryController {

    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private PaymentClient paymentClient;
    
    @GetMapping("/add")
    public ResponseEntity<String> addItemToInventory() {
        logger.info("Adding item to inventory...");
        return ResponseEntity.ok("Added");
    }

    @GetMapping("/remove")
    public ResponseEntity<String> removeItemFromInventory() {
        logger.info("Remove item from inventory...");
        return ResponseEntity.ok("removed");
    }

    @GetMapping("/order")
    public ResponseEntity<String> orderItemFromInventory() {
        logger.info("Ordering item from inventory...");
        try{
            ResponseEntity responseEntity = paymentClient.deductPayment();
            logger.info("Response from Payment is " + responseEntity);
        } catch (FeignException e) {
            logger.info("Failed in Payment");
            return ResponseEntity.internalServerError().body("Payment Failed");
        }
        return ResponseEntity.ok("removed");
    }

    
}
