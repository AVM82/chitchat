package com.group.chitchat.service;

import com.group.chitchat.model.dto.TopicDto;
import com.group.chitchat.repository.TopicRepo;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service that manages topics of chats.
 */
@Service
@AllArgsConstructor
public class TopicService {

  private final TopicRepo topicRepo;

  /**
   * Returns descending sorted list of topics.
   *
   * @return List of topics.
   */
  public List<TopicDto> getAllTopics() {

    return topicRepo.findAll().stream()
        .map(TopicDtoService::getFromEntity)
        .sorted(Comparator.comparing(TopicDto::getPriority).reversed())
        .toList();
  }
}
