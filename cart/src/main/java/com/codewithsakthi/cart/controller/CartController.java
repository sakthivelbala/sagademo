package com.codewithsakthi.cart.controller;

import org.springframework.web.bind.annotation.RestController;

import com.codewithsakthi.cart.feignclient.InventoryClient;

import feign.FeignException;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private InventoryClient inventoryClient;
    
    @GetMapping("/add")
    public ResponseEntity<String> addItemToCart() {
        logger.info("Adding item to cart...");
        return ResponseEntity.ok("Added");
    }

    @GetMapping("/remove")
    public ResponseEntity<String> removeItemFromCart() {
        logger.info("Remove item from cart...");
        return ResponseEntity.ok("removed");
    }

    @GetMapping("/order")
    public ResponseEntity<String> orderItemInCart() {
        logger.info("Removed item from cart and calling inventory...");
        try{
            ResponseEntity responseEntity = inventoryClient.orderItemFromInventory();
            logger.info("Response from Inventory " + responseEntity.getStatusCode());
        } catch(FeignException e){
            logger.info("Failed in Inventory");
            return ResponseEntity.internalServerError().body("Inventory Failed");
        }
        return ResponseEntity.ok("removed");
    }

    
}
