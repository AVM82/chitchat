package com.group.chitchat.service.chitchat;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import java.util.HashSet;

public class ChitchatDtoService {

  private ChitchatDtoService() {
  }

  /**
   * Building the Dto for response from Entity.
   *
   * @param chitchat entity of chitchat.
   * @return chitchatDto for response.
   */
  public static ChitchatForResponseDto getFromEntity(Chitchat chitchat) {
    return ChitchatForResponseDto.builder()
        .id(chitchat.getId())
        .chatName(chitchat.getChatName())
        .description(chitchat.getDescription())
        .languageName(chitchat.getLanguage().getName())
        .categoryName(chitchat.getCategory().getName())
        .level(chitchat.getLevel())
        .capacity(chitchat.getCapacity())
        .authorName(chitchat.getAuthor().getUsername())
        .date(chitchat.getDate())
        .usersInChitchat(chitchat.getUsersInChitchat().stream().map(User::getUsername).toList())
        .build();
  }

  /**
   * Creating the Entity with help of Dto for create Entity.
   *
   * @param chitchatDto special Dto for creating entity
   * @param author      User who created this chitchat
   * @param language    Language
   * @param category    Category
   * @return Entity of chitchat
   */
  public static Chitchat getFromDtoForCreate(
      ForCreateChitchatDto chitchatDto, User author,
      Language language, Category category) {
    return Chitchat.builder()
        .chatName(chitchatDto.getChatHeader())
        .description(chitchatDto.getDescription())
        .language(language)
        .category(category)
        .level(chitchatDto.getLevel())
        .capacity(chitchatDto.getCapacity())
        .author(author)
        .date(chitchatDto.getDate())
        .usersInChitchat(new HashSet<>())
        .build();
  }
}
