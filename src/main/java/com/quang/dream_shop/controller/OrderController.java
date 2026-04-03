package com.quang.dream_shop.controller;


import com.quang.dream_shop.dto.OrderDto;
import com.quang.dream_shop.model.Order;
import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.Order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {

    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order =  orderService.placeOrder(userId);
            OrderDto orderDto = orderService.convertToDto(order);
            return ResponseEntity.ok(new ApiResponse("Order placed successfully", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to place order", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(new ApiResponse("Order retrieved successfully", order));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to retrieve order", e.getMessage()));
        }
    }

    @GetMapping("/{userId}/order")
    public ResponseEntity<ApiResponse> getUserOrders(@PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok(new ApiResponse("User orders retrieved successfully", orders));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to retrieve user orders", e.getMessage()));
        }
    }

    @PutMapping("/{orderId}/cancel")
     public ResponseEntity<ApiResponse> cancelOrder(Long orderId) {
        try {
            orderService.cancelOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Order cancelled successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new ApiResponse("Failed to cancel order", e.getMessage()));
        }
    }

}
