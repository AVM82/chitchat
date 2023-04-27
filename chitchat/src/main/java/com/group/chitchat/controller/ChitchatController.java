package com.group.chitchat.controller;

import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.service.ResourcesBundleService;
import com.group.chitchat.service.chitchat.ChitchatService;
import com.group.chitchat.service.userdetails.CurrentUserService;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/chitchats")
@RequiredArgsConstructor
@Log4j2
public class ChitchatController {

  private final ChitchatService chitchatService;
  private final ResourcesBundleService resourceBundleService;

  /**
   * Takes a list of all chats.
   *
   * @param locale locale information is specified in the Accept-Language parameter.
   * @return list of all chats.
   */
  @GetMapping("/all")
  public ResponseEntity<List<ChitchatForResponseDto>> getAllChitchats(
      @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale locale
  ) {
    resourceBundleService.setLocale(locale);
    return ResponseEntity.ok(chitchatService.getAllChitchats());
  }

  /**
   * Creates and adds a new chat.
   *
   * @param forCreateChitchatDto An object that contains the necessary data to create a chat.
   * @param locale               locale information is specified in the Accept-Language parameter.
   * @return response about the status of creating a new chat.
   */
  @PostMapping
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      @RequestBody ForCreateChitchatDto forCreateChitchatDto,
      @RequestHeader(name = "Accept-Language", required = false, defaultValue = "en") Locale locale
  ) {
    resourceBundleService.setLocale(locale);
    return ResponseEntity.ok(chitchatService
        .addChitchat(forCreateChitchatDto, CurrentUserService.getCurrentUsername()));
  }

}
