package com.group.chitchat.controller;

import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.service.category.CategoryService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {

  private final CategoryService categoryService;
  private final LocaleResolverConfig localeResolverConfig;

  @GetMapping("/all")
  public ResponseEntity<List<CategoryDto>> getAllCategories(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return categoryService.getAllCategories();
  }

  @GetMapping("{categoryId}")
  public ResponseEntity<CategoryDto> getOneCategory(
      @PathVariable("categoryId") Integer categoryId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return categoryService.getOneCategory(categoryId);
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDto> addCategory(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody CategoryDto categoryDto
  ) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return categoryService.addCategory(categoryDto);
  }

  @PutMapping("{categoryId}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDto> updateCategory(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @PathVariable("categoryId") Integer categoryId,
      @RequestBody CategoryDto categoryDto
  ) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return categoryService.changeCategory(categoryId, categoryDto);
  }
}
