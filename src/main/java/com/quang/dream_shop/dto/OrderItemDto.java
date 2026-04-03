package com.quang.dream_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productName;
    private String productBrand;
    private Integer quantity;
    private BigDecimal unitPrice;
}


