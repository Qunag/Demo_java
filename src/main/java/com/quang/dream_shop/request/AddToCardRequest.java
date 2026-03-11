package com.quang.dream_shop.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AddToCardRequest {
    private Long productId;
    private int quantity;


}
