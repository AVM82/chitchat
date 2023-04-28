package com.group.chitchat.controller;

import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.service.chitchat.ChitchatService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.userdetails.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  private final LocaleResolverConfig localeResolverConfig;

  /**
   * Takes a list of all chats.
   *
   * @param requestHeader An object for obtaining request header parameters.
   * @return list of all chats.
   */
  @GetMapping("/all")
  public ResponseEntity<List<ChitchatForResponseDto>> getAllChitchats(
      HttpServletRequest requestHeader) {
    localeResolverConfig.setLocale(requestHeader, null, null);
    return ResponseEntity.ok(chitchatService.getAllChitchats());
  }

  @GetMapping("{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> getChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader) {
    localeResolverConfig.setLocale(requestHeader, null, null);
    return chitchatService.getChitchat(chitchatId);
  }

  /**
   * Creates and adds a new chat.
   *
   * @param forCreateChitchatDto An object that contains the necessary data to create a chat.
   * @param requestHeader        An object for obtaining request header parameters.
   * @return response about the status of creating a new chat.
   */
  @PostMapping
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      @RequestBody ForCreateChitchatDto forCreateChitchatDto,
      HttpServletRequest requestHeader) {
    localeResolverConfig.setLocale(requestHeader, null, null);
    return ResponseEntity.ok(chitchatService
        .addChitchat(forCreateChitchatDto, CurrentUserService.getCurrentUsername()));
  }

}
