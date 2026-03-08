package com.quang.dream_shop.service.product;

import com.quang.dream_shop.exception.AlreadyExistsException;
import com.quang.dream_shop.exception.ProductNotFoundException;
import com.quang.dream_shop.model.Category;
import com.quang.dream_shop.model.Product;
import com.quang.dream_shop.repository.CategoryRepository;
import com.quang.dream_shop.repository.ProductRepository;
import com.quang.dream_shop.request.AddProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product addProduct(AddProductRequest request ) {
        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException( "Product with the same name " + request.getName() +" " +
                    "and brand " + request.getBrand()+ " already exists.");
        }
//        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
//                .orElseGet(() -> {
//                    Category newCategory = new Category(request.getCategory().getName());
//                    return categoryRepository.save(newCategory);
//
//                });
//        request.setCategory((category));
//
//        return productRepository.save(createProduct(request , category));
        return null;

    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name, brand);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(product.getName());
            existingProduct.setBrand(product.getBrand());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setCategory(product.getCategory());
            return productRepository.save(existingProduct);
        }).orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse( productRepository::delete, ()  -> {
            throw new ProductNotFoundException("Product not found");
        });


    }

    @Override
    public List<Product> getAllPProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory_Name(category);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategory_NameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }
}
