package com.quang.dream_shop.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemDto {
    private Long itemId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private ProductDto product;
}
