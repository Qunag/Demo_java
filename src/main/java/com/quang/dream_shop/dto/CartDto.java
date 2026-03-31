package com.quang.dream_shop.dto;

import com.quang.dream_shop.dto.CartItemDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDto {
    private Long cartId;
    private List<CartItemDto> items;
    private BigDecimal totalAmount;
}