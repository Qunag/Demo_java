package com.quang.dream_shop.controller;

import com.quang.dream_shop.dto.ProductDto;
import com.quang.dream_shop.exception.AlreadyExistsException;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Product;
import com.quang.dream_shop.request.AddProductRequest;
import com.quang.dream_shop.request.ProductUpdateRequest;
import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.product.IProductService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private final IProductService productService ;
    private final ModelMapper modelMapper;

    @GetMapping("/product/all")
    public ResponseEntity<ApiResponse> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the products", e.getMessage() ));
        }

    }


    @GetMapping("/product/{productId}/getById")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable long productId) {
        try {
            Product products = productService.getProductById(productId);
            ProductDto productDto = productService.ConvertToProductDto(products);
            if (products.getId() == null) {
                return ResponseEntity.status(404)
                        .body(new ApiResponse("Product not found with id: " + productId, null));
            }
            return ResponseEntity.ok(new ApiResponse("Product retrieved successfully", productDto));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the product", e.getMessage()));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product) {
        try {
            Product theProduct =productService.addProduct(product) ;
            return ResponseEntity.ok(new ApiResponse("Add product successfully", theProduct));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(409)
                    .body(new ApiResponse("Failed to add product", e.getMessage()));
        }
    }
    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest request, @PathVariable Long productId) {
        try {
            Product updatedProduct = productService.updateProduct(request , productId);
            return ResponseEntity.ok(new ApiResponse("Update product successfully", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("Product not found", e.getMessage()));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse("Product not found", e.getMessage()));
        }
    }


    @GetMapping("/product/by-name/")
    public ResponseEntity<ApiResponse> getProductByName(@RequestParam String name){
        try{
            List<Product> products = productService.getProductsByName(name);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(new ApiResponse("Product not found with name: " + name, null));
            }
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the products", e.getMessage()));
        }
    }

    @GetMapping("/product/by-brand/")
    public ResponseEntity<ApiResponse> getProductsByBrand(@RequestParam String brand){
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()){
                return  ResponseEntity.status(404).body(new ApiResponse("No products found for the given brand", null));
            }
            return ResponseEntity.ok(new ApiResponse("Get products by brand successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("Failed to get products by brand", e.getMessage()));
        }
    }

    @GetMapping("/product/{category}/products/all")
    public ResponseEntity<ApiResponse> getProductsByCategory(@RequestParam String category){
        try{
            List<Product> products = productService.getProductsByCategory(category);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            if (products.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(new ApiResponse("Product not found with category: " + category, null));
            }
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the products", e.getMessage()));
        }
    }

    @GetMapping("/product/category-and-brand")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand){
        try{
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the products", e.getMessage()));
        }
    }

    @GetMapping("/product/brand-and-name")
    public ResponseEntity<ApiResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            List<ProductDto> convertedProducts = productService.getConvertedProducts(products);
            return ResponseEntity.ok(new ApiResponse("Products retrieved successfully", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the products", e.getMessage()));
        }
    }

    @GetMapping("/product/count/by-brand-and-name")
    public ResponseEntity<ApiResponse> countProductsByBrandAndName(@RequestParam String brand, @RequestParam String name){
        try{
            Long count = productService.countProductsByBrandAndName(brand, name);
            return ResponseEntity.ok(new ApiResponse("Count retrieved successfully", count));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the count", e.getMessage()));
        }
    }




}
