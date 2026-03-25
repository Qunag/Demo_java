package com.quang.dream_shop.service.Order;

import com.quang.dream_shop.dto.OrderDto;
import com.quang.dream_shop.model.Order;

import java.util.List;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrderById(Long orderId);


    List<OrderDto> getUserOrders(Long userId);

    void cancelOrder(Long orderId);

}
