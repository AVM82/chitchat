package com.group.chitchat.controller;

import com.group.chitchat.model.dto.InternalChatMessageDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
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
   *
   * @param message A message
   */

  @MessageMapping("/send/message/{id}")
  public void sendMessage(@Payload InternalChatMessageDto message, @DestinationVariable String id) {
    // TODO save to database
    log.info(message);
    this.template.convertAndSend("/message." + id,
        message);
  }
}
