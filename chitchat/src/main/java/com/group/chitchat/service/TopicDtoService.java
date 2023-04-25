package com.group.chitchat.service;

import com.group.chitchat.model.Topic;
import com.group.chitchat.model.dto.TopicDto;

public class TopicDtoService {

  public static TopicDto getFromEntity(Topic topic) {
    return new TopicDto(topic.getId(), topic.getName(), topic.getPriority());
  }

  public Topic getFromDto(TopicDto topicDto) {
    return new Topic(topicDto.getTopicName());
  }
}
