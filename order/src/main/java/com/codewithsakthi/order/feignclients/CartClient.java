package com.codewithsakthi.order.feignclients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "CART") 
public interface CartClient {

    @GetMapping("/cart/add")
    public ResponseEntity addItemToCart();
    @GetMapping("/cart/remove")
    public ResponseEntity removeItemToCart();
    @GetMapping("/cart/order")
    public ResponseEntity orderItemInCart();
    
}
