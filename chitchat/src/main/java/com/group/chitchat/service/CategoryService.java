package com.group.chitchat.service;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.repository.CategoryRepo;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service that manages topics of chats.
 */
@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepo categoryRepo;

  /**
   * Returns descending sorted list of categories.
   *
   * @return List of categories.
   */
  public ResponseEntity<List<CategoryDto>> getAllCategories() {

    return ResponseEntity.ok(
        categoryRepo.findAll().stream()
            .map(CategoryDtoService::getFromEntity)
            .sorted(Comparator.comparing(CategoryDto::getPriority).reversed())
            .toList());
  }

  /**
   * Returns one category by id.
   *
   * @param categoryId id of category.
   * @return category by id.
   */
  public ResponseEntity<CategoryDto> getOneCategory(Integer categoryId) {
    return ResponseEntity.ok(
        CategoryDtoService.getFromEntity(categoryRepo.findById(categoryId).orElseThrow()));
  }

  /**
   * Adds new category to database.
   *
   * @param categoryDto Incoming dto with data for adding to database;
   * @return Response with status and body.
   */
  public ResponseEntity<CategoryDto> addCategory(CategoryDto categoryDto) {
    Category category = CategoryDtoService.getFromDto(categoryDto);
    categoryRepo.save(category);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(CategoryDtoService.getFromEntity(category));
  }

  /**
   * Changes category by id to new data from incoming dto.
   *
   * @param categoryId  id of category.
   * @param categoryDto Incoming dto with data for adding to database.
   * @return Response with status and body with changed category.
   */
  @Transactional
  public ResponseEntity<CategoryDto> changeCategory(Integer categoryId, CategoryDto categoryDto) {
    Category category = categoryRepo.findById(categoryId).orElseThrow();
    category.setName(categoryDto.getName());

    return ResponseEntity.ok(CategoryDtoService.getFromEntity(category));
  }
}
