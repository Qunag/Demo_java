package com.quang.dream_shop.controller;


import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.model.CartItem;
import com.quang.dream_shop.model.User;
import com.quang.dream_shop.response.ApiResponse;

import com.quang.dream_shop.service.Cart.CartService;
import com.quang.dream_shop.service.Cart.ICartItemService;
import com.quang.dream_shop.service.Cart.ICartService;
import com.quang.dream_shop.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cart-items")
public class CartItemController {

    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;



    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {
        try {
            User user = userService.getAuthenticatedUser();
            Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item added to cart successfully", null));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404).body(new ApiResponse(e.getMessage(), null));
        }
        catch (JwtException e){
            return ResponseEntity.status(401).body(new ApiResponse("Unauthorized access", e.getMessage()));

        }
    }



    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> RemoveItemFromCart(@PathVariable Long cartId ,@PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse("Item removed from cart successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error removing item from cart", e.getMessage()));
        }
    }


    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateQuantity(@PathVariable  Long cartId,@PathVariable Long itemId,@RequestParam int quantity) {
        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse("Item quantity updated successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(500).body(new ApiResponse("Error updating item quantity", e.getMessage()));
        }
    }
}
