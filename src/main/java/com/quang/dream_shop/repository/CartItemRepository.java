package com.quang.dream_shop.repository;

import com.quang.dream_shop.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId);

    void deleteAllByCartId(Long cartId);
}
