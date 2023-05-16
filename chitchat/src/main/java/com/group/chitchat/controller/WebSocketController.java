package com.group.chitchat.controller;

import com.group.chitchat.model.dto.InternalChatMessageDto;
import java.time.LocalDateTime;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@Log4j2
public class WebSocketController {

  private final SimpMessagingTemplate template;

  @Autowired
  WebSocketController(SimpMessagingTemplate template) {
    this.template = template;
  }

  /**
   * Send message endpoint.
   * @param message A message
   */

  @MessageMapping("/send/message")
  public void sendMessage(String message) {
    // TODO save to database
    String[] strings = message.split("##");
    InternalChatMessageDto internalChatMessageDto = new InternalChatMessageDto(
        LocalDateTime.now(),
        strings[0],
        Long.parseLong(strings[1]),
        strings[2]);
    log.info(internalChatMessageDto);
    this.template.convertAndSend("/message", internalChatMessageDto.toString());
  }
}
