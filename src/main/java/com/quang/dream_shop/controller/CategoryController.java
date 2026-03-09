package com.quang.dream_shop.controller;


import com.quang.dream_shop.exception.AlreadyExistsException;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Category;
import com.quang.dream_shop.response.ApiResponse;

import com.quang.dream_shop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/category/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Categories retrieved successfully", categories));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving categories", null));
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId){
        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(404)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the category", null));
        }
    }


    @GetMapping("/category/{name}")
    public ResponseEntity<ApiResponse> getCategoryByName(@RequestParam String name){
        try {
            Category category = categoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Success", category));
        }
        catch(ResourceNotFoundException e){
            return ResponseEntity.status(404)
                    .body(new ApiResponse(e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while retrieving the category", null));
        }
    }   

    @PostMapping("/category/add")
    public ResponseEntity<ApiResponse> addCategory(Category category){
        try {
            Category newCategory = categoryService.addCategory(category);
            return ResponseEntity.ok(new ApiResponse("Category added successfully", newCategory));
        }
        catch (AlreadyExistsException e) {
            return ResponseEntity.status(400)
                    .body(new ApiResponse("Category already exists", null));
        }catch(Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while adding the category", null));
        }
    }

    @PutMapping("/category/{categoryId}/update")
    public ResponseEntity<ApiResponse> updateCategory(@RequestParam Category category, @RequestParam Long categoryId){
        try {
            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok(new ApiResponse("Category updated successfully", updatedCategory));
        }
        catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse(e.getMessage(), null));
        }catch(Exception e) {
            return ResponseEntity.status(500)
                    .body(new ApiResponse("An error occurred while updating the category", null));
        }
    }

    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Category deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(404)
                    .body(new ApiResponse(e.getMessage(), null));
        }
    }


}
