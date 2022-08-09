package com.example.taobao_be.controllers;

import com.example.taobao_be.Constants;
import com.example.taobao_be.DTO.CategoryDTO;
import com.example.taobao_be.DTO.PageDTO;
import com.example.taobao_be.DTO.ResponseDTO;
import com.example.taobao_be.Services.CategoryService;
import com.example.taobao_be.models.Category;
import com.example.taobao_be.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/admin")
@CrossOrigin(Constants.baseUrlFE)
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("categories")
    public ResponseEntity<ResponseDTO> getPageCategories(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        PageDTO<CategoryDTO> items = this.categoryService.getAll(offset, limit);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("categories", items))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }

    @GetMapping("categories/{id}")
    public ResponseEntity<ResponseDTO> getCategoriesById(@PathVariable("id") Long id) {
        try {
            CategoryDTO categoryDTO = this.categoryService.getById(id);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("category", categoryDTO))
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

    @PutMapping("categories")
    public ResponseEntity<ResponseDTO> updateCategory(@RequestBody Category category) {
        try {
            CategoryDTO categoryDTO = this.categoryService.update(category);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("category", categoryDTO))
                            .statusCode(OK.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("categories")
    public ResponseEntity<ResponseDTO> insertCategory(@RequestBody Category category) {
        try {
            CategoryDTO categoryDTO = this.categoryService.create(category);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(CREATED)
                            .data(Map.of("category", categoryDTO))
                            .statusCode(CREATED.value())
                            .timeStamp(LocalDateTime.now())
                            .build()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("categories/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable("id") Long id) {
        try {
            Category category = this.categoryService.getCategoryById(id);
            CategoryDTO categoryDTO = this.categoryService.delete(category);
            return ResponseEntity.ok(
                    ResponseDTO.builder()
                            .status(OK)
                            .data(Map.of("category", categoryDTO))
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
    @GetMapping("searchCategories")
    public ResponseEntity<ResponseDTO> searchCategories(@RequestParam("name") String name) {
        List<CategoryDTO> categoryDTOList = this.categoryService.searchCategoriesByName(name);
        return ResponseEntity.ok(
                ResponseDTO.builder()
                        .status(OK)
                        .data(Map.of("categories", categoryDTOList))
                        .statusCode(OK.value())
                        .timeStamp(LocalDateTime.now())
                        .build()
        );
    }
}
