package com.quang.dream_shop.service.Cart;


import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Cart;
import com.quang.dream_shop.model.User;
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
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }



    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;

    }
    @Override
    @Transactional
    public Cart initializeNewCart(User user) {
        Cart existingCart = cartRepository.findByUserId(user.getId());
        if (existingCart != null) {
            return existingCart;
        }

        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setTotalAmount(BigDecimal.ZERO);
        return cartRepository.save(newCart);
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
