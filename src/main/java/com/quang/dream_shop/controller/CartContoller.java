package com.quang.dream_shop.controller;


import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.Cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.parser.Entity;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartContoller {
    private final CartService cartService;


    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long cartId){
        try {
            Cart cart =  cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Get cart successfully", cart));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Get cart failed", e.getMessage()));
        }
    }

    @GetMapping("/cart/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId){

        Cart cart = cartService.getCart(cartId);
        if (cart != null) {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Clear cart successfully", null));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse("Cart not found", null));
        }
    }

    @GetMapping("/cart/{cartId}/total-price")
    public ResponseEntity<ApiResponse> getTotalPrice(@PathVariable Long cartId) {
        try {
            return ResponseEntity.ok(new ApiResponse("Get total price successfully", cartService.getTotalPrice(cartId)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Get total price failed", e.getMessage()));
        }
    }

    public
}
