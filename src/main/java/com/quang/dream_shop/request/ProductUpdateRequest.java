package com.quang.dream_shop.request;

import com.quang.dream_shop.model.Category;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductUpdateRequest {
    private Long id ;
    private String name;
    private String brand ;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
