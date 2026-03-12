package com.quang.dream_shop.service.Cart;


import com.quang.dream_shop.model.Cart;



import java.math.BigDecimal;


public interface ICartService {
    Cart getCart(Long cartId);
    void clearCart(Long cartId);
    BigDecimal getTotalPrice(Long cartId);

}
