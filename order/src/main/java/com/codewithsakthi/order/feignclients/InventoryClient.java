package com.codewithsakthi.order.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "INVENTORY") 
public interface InventoryClient {

    @GetMapping("/inventory/add")
    public ResponseEntity addItemToInventory();
    @GetMapping("inventory/remove")
    public ResponseEntity<String> removeItemFromInventory();
    
}