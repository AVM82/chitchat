package com.group.chitchat.service.category;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.repository.CategoryRepo;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
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
  public List<CategoryDto> getAllCategories() {

    return categoryRepo.findAll().stream()
        .map(CategoryDtoService::getFromEntity)
        .sorted(Comparator.comparing(CategoryDto::getPriority).reversed())
        .toList();
  }

  /**
   * Returns one category by id.
   *
   * @param categoryId id of category.
   * @return category by id.
   */
  public CategoryDto getOneCategory(Integer categoryId) {

    return CategoryDtoService.getFromEntity(categoryRepo.findById(categoryId).orElseThrow());
  }

  /**
   * Adds new category to database.
   *
   * @param categoryDto Incoming dto with data for adding to database;
   * @return Response with status and body.
   */
  public CategoryDto addCategory(CategoryDto categoryDto) {
    Category category = CategoryDtoService.getFromDto(categoryDto);
    categoryRepo.save(category);

    return CategoryDtoService.getFromEntity(category);
  }

  /**
   * Changes category by id to new data from incoming dto.
   *
   * @param categoryId  id of category.
   * @param categoryDto Incoming dto with data for adding to database.
   * @return changed category.
   */
  @Transactional
  public CategoryDto changeCategory(Integer categoryId, CategoryDto categoryDto) {
    Category category = categoryRepo.findById(categoryId).orElseThrow();
    category.setName(categoryDto.getName());

    return CategoryDtoService.getFromEntity(category);
  }
}
