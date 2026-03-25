package com.quang.dream_shop.service.Order;

import com.quang.dream_shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    Order getOrderById(Long orderId);

    List<Order> getUserOrders(Long userId);

    void cancelOrder(Long orderId);

}
