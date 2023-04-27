package com.group.chitchat.service.category;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.dto.CategoryDto;

public class CategoryDtoService {

  private CategoryDtoService() {
  }

  public static CategoryDto getFromEntity(Category category) {
    return new CategoryDto(category.getId(), category.getName(), category.getPriority());
  }

  public static Category getFromDto(CategoryDto categoryDto) {
    return new Category(categoryDto.getName());
  }
}
