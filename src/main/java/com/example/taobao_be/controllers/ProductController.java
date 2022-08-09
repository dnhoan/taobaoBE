package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.CategoryDTO;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ProductDTO;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.Services.ProductService;
import com.example.taobao_be.models.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<ResponseDTO> getPageProduct(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<ProductDTO> productDTOPageDTO = this.productService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .statusCode(OK.value())
                        .data(Map.of("products", productDTOPageDTO))
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("products/{id}")
    public ResponseEntity<ResponseDTO> getProductById(@PathVariable("id") Long id) {
        try {
            ProductDTO productDTO = this.productService.getById(id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("product", productDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(NO_CONTENT)
                            .data(Map.of("message", "Category not exist"))
                            .statusCode(NO_CONTENT.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }


    @PostMapping("products")
    public ResponseEntity<ResponseDTO> createProduct(@RequestBody Product product) {
        try {
            ProductDTO productDTO = this.productService.create(product);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(CREATED)
                            .statusCode(CREATED.value())
                            .data(Map.of("product", productDTO))
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi tạo sản phẩm")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }
    @PutMapping("products")
    public ResponseEntity<ResponseDTO> updateProduct(@RequestBody Product product) {
        try {
            ProductDTO productDTO = this.productService.update(product);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .statusCode(OK.value())
                            .data(Map.of("product", productDTO))
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(
                    ResponseDTO.builder()
                            .message("Lỗi cập nhật sản phẩm")
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        }
    }

}
