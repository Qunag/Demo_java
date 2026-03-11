package com.quang.dream_shop.security.config;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ShopConfig {
//    private static final List<String> SECURED_URLS = List.of(
//            "/api/v1/users/**",
//            "/api/v1/products/**",
//            "/api/v1/orders/**",
//            "/api/v1/carts/**",
//            "/api/v1/cartItems/**",
//            "/api/v1/categories/**"
//    );

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
