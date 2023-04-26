package com.group.chitchat.controller;

import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")
@RequestMapping("/api/v1/category")
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    return categoryService.getAllCategories();
  }

  @GetMapping("{categoryId}")
  public ResponseEntity<CategoryDto> getOneCategory(
      @PathVariable("categoryId") Integer categoryId) {
    return categoryService.getOneCategory(categoryId);
  }

  @PostMapping
  public ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto) {
    return categoryService.addCategory(categoryDto);
  }

  @PutMapping("{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Integer categoryId,
      @RequestBody CategoryDto categoryDto) {

    return categoryService.changeCategory(categoryId, categoryDto);
  }
}
