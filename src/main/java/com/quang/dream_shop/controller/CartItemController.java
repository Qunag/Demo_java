package com.quang.dream_shop.controller;


import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.model.CartItem;
import com.quang.dream_shop.response.ApiResponse;

import com.quang.dream_shop.service.Cart.CartService;
import com.quang.dream_shop.service.Cart.ICartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final CartService cartService;


    @PostMapping("/item/add-to-cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestParam Long cartId , @RequestParam Long productId , @RequestParam int quantity){

            try {
                Cart cart = cartService.getCart(cartId);
                CartItem cartItem = cart.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElse(null);
                if (cartItem != null) {
                    quantity += cartItem.getQuantity();
                }
                if (cartId == null ) {
                    cartId = cartService.getCartIdGenerator().get();
                }
                cartItemService.addItemToCart(cartId, productId, quantity);
                return ResponseEntity.ok(new ApiResponse("Add item to cart successfully", null));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(new ApiResponse("Add item to cart failed", e.getMessage()));
            }

    }

    @DeleteMapping("/item/{productId}/remove/cart/{cartId}/")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId , @PathVariable Long productId){

        try {
            cartItemService.removeItemFromCart(cartId, productId);
            return ResponseEntity.ok(new ApiResponse("Remove item from cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse("Remove item from cart failed", e.getMessage()));
        }

    }


    @PutMapping("/item/{productId}/update/cart/{cartId}/")
    public ResponseEntity<ApiResponse> updateCartItem(@PathVariable Long cartId ,@PathVariable Long productId , @RequestParam int quantity){
            try {
                if (cartId == null ) {
                    cartId = cartService.getCartIdGenerator().get();
                }
                cartItemService.updateItemQuantity(cartId, productId, quantity);
                return ResponseEntity.ok(new ApiResponse("Update item in cart successfully", null));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(404).body(new ApiResponse( e.getMessage(), null));
            }
    }
}
