package com.codewithsakthi.order.controller;

import org.springframework.web.bind.annotation.RestController;

import com.codewithsakthi.order.feignclients.CartClient;
import com.codewithsakthi.order.feignclients.InventoryClient;
import com.codewithsakthi.order.feignclients.PaymentClient;

import feign.FeignException;
import feign.FeignException.FeignClientException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CartClient cartClient;
    @Autowired
    private InventoryClient inventoryClient;
    @Autowired
    private PaymentClient paymentClient;

    @GetMapping("/orchestrate/order")
    public String orchestrateOrder() {
        ResponseEntity cartResponse = cartClient.removeItemToCart();
        logger.info("Response from cart is " + cartResponse.getStatusCode());
        ResponseEntity inventoryResponse = inventoryClient.removeItemFromInventory();
        logger.info("Response from Inventory is " + inventoryResponse.getStatusCode());
        try{
            ResponseEntity paymentResponse = paymentClient.deductPayment();
            logger.info("Response from Payment is " + paymentResponse.getStatusCode());
        } catch(FeignException e){
            logger.error("Failed payments :", e);
            logger.info("Triggering rollback");
            ResponseEntity inventoryRollbackResponse = inventoryClient.addItemToInventory();
            logger.info("Rollback Response from Inventory is " + inventoryRollbackResponse.getStatusCode());
            ResponseEntity cartRollbackResponse = cartClient.addItemToCart();
            logger.info("Rollback Response from cart is " + cartRollbackResponse.getStatusCode());
        }
        return "Orchestrated !";
    }

    @GetMapping("/choreography/order")
    public String choreographyOrder() {
        try{
            ResponseEntity cartResponse = cartClient.orderItemInCart();
            logger.info("Response from cart is " + cartResponse.getStatusCode());
        } catch(FeignException e){
            logger.error("Failed Cart :", e);
            logger.info("Triggering rollback for Cart");
            ResponseEntity cartRollbackResponse = cartClient.addItemToCart();
            logger.info("Rollback Response from cart is " + cartRollbackResponse.getStatusCode());
        }
        return "Orchestrated !";
    }
    
    
}
