package com.group.chitchat.controller;

import com.group.chitchat.model.dto.TopicDto;
import com.group.chitchat.service.TopicService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class TopicController {

  private final TopicService topicService;

  @GetMapping
  public ResponseEntity<List<TopicDto>> getAllTopics() {
    return ResponseEntity.ok(topicService.getAllTopics());
  }
}
