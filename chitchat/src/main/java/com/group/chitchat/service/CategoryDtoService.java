package com.group.chitchat.service;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.dto.CategoryDto;

public class CategoryDtoService {

  public static CategoryDto getFromEntity(Category category) {
    return new CategoryDto(category.getId(), category.getName(), category.getPriority());
  }

  public Category getFromDto(CategoryDto categoryDto) {
    return new Category(categoryDto.getCategoryName());
  }
}
