package com.quang.dream_shop.controller;

import com.quang.dream_shop.dto.ImageDto;
import com.quang.dream_shop.exception.ResourceNotFoundException;
import com.quang.dream_shop.model.Image;
import com.quang.dream_shop.response.ApiResponse;
import com.quang.dream_shop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage (@RequestParam List<MultipartFile> files , @RequestParam Long id) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, id);
            return ResponseEntity.ok(new ApiResponse("Upload images successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload image failed" , e.getMessage()));
        }

    }

    @GetMapping ("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage (@PathVariable Long id) {
        try {
            Image image = imageService.getImageById(id);
            ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1 , (int) image.getImage().length()));
            return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }


    @PutMapping("image/{imageId}/update")
    public ResponseEntity<ApiResponse> updateImage (@RequestBody MultipartFile files , @PathVariable Long imageId) {
        Image image = imageService.getImageById(imageId);
        try {
            if (image != null) {
                imageService.updateImage(files, imageId);
                return ResponseEntity.ok(new ApiResponse("Update image successfully", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Update image failed" , "Image not found with id: " + imageId));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse("Update image failed" , "An error occurred while updating the image"));

    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<ApiResponse> deleteImage (@PathVariable Long imageId){
        try {
            imageService.deleteImageById(imageId);
            return ResponseEntity.ok(new ApiResponse("Delete image successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse("Delete image failed" , "Image not found with id: " + imageId));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Delete image failed" , "An error occurred while deleting the image"));
        }
    }


}
