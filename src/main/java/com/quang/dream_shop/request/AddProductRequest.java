package com.quang.dream_shop.request;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddProductRequest {
    private Long id ;
    private String name;
    private String brand ;
    private BigDecimal price;
    private int inventory;
    private String description;
    private String category;

}
