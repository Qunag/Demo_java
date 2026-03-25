package com.quang.dream_shop.service.Order;

import com.quang.dream_shop.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId, Order order);
    Order getOrderById(int orderId);
    void cancelOrder(int orderId);

}
