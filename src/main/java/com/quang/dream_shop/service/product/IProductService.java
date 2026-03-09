package com.quang.dream_shop.service.product;

import com.quang.dream_shop.dto.ProductDto;
import com.quang.dream_shop.model.Product;
import com.quang.dream_shop.request.AddProductRequest;
import com.quang.dream_shop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    Product getProductById(Long id);
    Product updateProduct(ProductUpdateRequest request,Long id);
    void deleteProductById(Long id);

    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);

    List<ProductDto> getConvertedProducts(List<Product> products);

    ProductDto ConvertToProductDto(Product product);


}


