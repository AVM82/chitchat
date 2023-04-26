package com.group.chitchat.service;

import com.group.chitchat.model.dto.CategoryDto;
import com.group.chitchat.repository.CategoryRepo;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
