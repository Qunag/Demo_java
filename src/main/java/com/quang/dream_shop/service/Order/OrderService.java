package com.quang.dream_shop.service.Order;

import com.quang.dream_shop.enums.OrderStatus;
import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.model.Order;
import com.quang.dream_shop.model.OrderItem;
import com.quang.dream_shop.model.Product;
import com.quang.dream_shop.repository.OrderRepository;
import com.quang.dream_shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;


    private Order createOrder (Cart cart){
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return order;
    }


    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList) {
        return orderItemList.stream().map(item ->  item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    private List<OrderItem> createOrderItems (Order order , Cart cart){

        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice()
                    );

        }).toList();
    }

    
    @Override
    public Order placeOrder(Long userId, Order order) {
        return null;
    }

    @Override
    public Order getOrderById(int orderId) {
        return null;
    }

    @Override
    public void cancelOrder(int orderId) {

    }
}
