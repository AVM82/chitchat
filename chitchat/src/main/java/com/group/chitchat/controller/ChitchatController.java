package com.group.chitchat.controller;

import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.service.chitchat.ChitchatService;
import com.group.chitchat.service.userdetails.CurrentUserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chitchats")
@RequiredArgsConstructor
@Log4j2
public class ChitchatController {

  private final ChitchatService chitchatService;

  @GetMapping("/all")
  public ResponseEntity<List<ChitchatForResponseDto>> getAllChitchats() {
    return ResponseEntity.ok(chitchatService.getAllChitchats());
  }

  @PostMapping
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      @RequestBody ForCreateChitchatDto forCreateChitchatDto) {
    return ResponseEntity.ok(chitchatService
        .addChitchat(forCreateChitchatDto, CurrentUserService.getCurrentUsername()));
  }

}
