package com.quang.dream_shop.service.Order;

import com.quang.dream_shop.dto.OrderDto;
import com.quang.dream_shop.enums.OrderStatus;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.model.Order;
import com.quang.dream_shop.model.OrderItem;
import com.quang.dream_shop.model.Product;
import com.quang.dream_shop.repository.OrderRepository;
import com.quang.dream_shop.repository.ProductRepository;
import com.quang.dream_shop.service.Cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ICartService cartService;
    private final ModelMapper modelMapper;


    private Order createOrder (Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
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
    public Order placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Order order = createOrder(cart);
        List<OrderItem> orderItemsList = createOrderItems(order, cart);

        order.setOrderItems(new HashSet<>(orderItemsList));
        order.setTotalAmount(calculateTotalAmount(orderItemsList));

        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(cart.getId());
        return savedOrder;

    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this :: convertToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
    }
    @Override
    public List<OrderDto> getUserOrders(Long userId) {
        List<Order> orders =  orderRepository.findByUserId(userId);
        return  orders.stream().map(this :: convertToDto).toList();

    }

    @Override
    public void cancelOrder(Long orderId) {
            Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
            if (order.getOrderStatus() == OrderStatus.PENDING) {
                order.setOrderStatus(OrderStatus.CANCELED);
                orderRepository.save(order);
            } else {
                throw new RuntimeException("Only pending orders can be canceled");
            }

    }

    private OrderDto convertToDto(Order order) {
        return modelMapper.map(order, OrderDto.class);
    }
}
