package com.quang.dream_shop.service.Cart;


import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.repository.CartItemRepository;
import com.quang.dream_shop.repository.CartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);


    @Override
    public Cart getCart(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount() ;
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long cartId) {
        Cart cart = getCart(cartId);
        cartItemRepository.deleteAllByCartId(cartId);
        cart.getItems().clear();
        cartRepository.deleteById(cartId);

    }

    @Override
    public BigDecimal getTotalPrice(Long cartId) {
        Cart cart = getCart(cartId);
        return cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;
    }


    public AtomicLong getCartIdGenerator() {
        Cart newCart = new Cart();
        newCart.setId(cartIdGenerator.incrementAndGet());
        cartRepository.save(newCart);
        return cartIdGenerator;
    }
}
