package com.quang.dream_shop.service.Cart;

import com.quang.dream_shop.model.Cart;

import java.math.BigDecimal;

public interface ICartItemService {
    void addItemToCart(Long cartId, Long productId, int quantity);

    void updateItemQuantity(Long cartId, Long productId, int quantity);

    void removeItemFromCart(Long cartId, Long productId);

}
