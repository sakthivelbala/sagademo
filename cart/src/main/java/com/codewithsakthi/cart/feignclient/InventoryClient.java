package com.codewithsakthi.cart.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "INVENTORY") 
public interface InventoryClient {
    @GetMapping("/inventory/order")
    public ResponseEntity orderItemFromInventory();
    
}
