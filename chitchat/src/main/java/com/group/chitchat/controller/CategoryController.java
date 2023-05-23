package com.group.chitchat.controller;

import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.service.category.CategoryService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  /**
   * Returns descending sorted list of categories.
   *
   * @return List of categories.
   */
  @GetMapping("/all")
  public ResponseEntity<List<CategoryDto>> getAllCategories(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        categoryService.getAllCategories());
  }

  /**
   * Returns one category by id.
   *
   * @param categoryId id of category.
   * @return Response with category by id.
   */
  @GetMapping("{categoryId}")
  public ResponseEntity<CategoryDto> getOneCategory(
      @PathVariable("categoryId") Integer categoryId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        categoryService.getOneCategory(categoryId));
  }

  /**
   * Adds new category to database.
   *
   * @param requestHeader header.
   * @param response      response.
   * @param categoryDto   dto with new category name.
   * @return response with new category.
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDto> addCategory(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @RequestBody CategoryDto categoryDto) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.status(HttpStatus.CREATED).body(
        categoryService.addCategory(categoryDto));
  }

  /**
   * Adds new category to database.
   *
   * @param categoryDto Incoming dto with data for adding to database;
   * @return Response with status and body.
   */
  @PutMapping("{categoryId}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<CategoryDto> updateCategory(
      HttpServletRequest requestHeader, HttpServletResponse response,
      @PathVariable("categoryId") Integer categoryId,
      @RequestBody CategoryDto categoryDto) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        categoryService.changeCategory(categoryId, categoryDto));
  }
}
