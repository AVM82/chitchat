package com.group.chitchat.controller;

import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.service.chitchat.ChitchatService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.userdetails.CurrentUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
   * @param response      object that sets the locale.
   * @return list of all chats.
   */
  @GetMapping("/all")
  public ResponseEntity<List<ChitchatForResponseDto>> getAllChitchats(
      @RequestParam(value = "languageId", required = false) String languageId,
      @RequestParam(value = "levelId", required = false) String level,
      @RequestParam(value = "dateFrom", required = false) LocalDateTime dateFrom,
      @RequestParam(value = "dateTo", required = false) LocalDateTime dateTo,
      HttpServletRequest requestHeader, HttpServletResponse response) {

    localeResolverConfig.setLocale(requestHeader, response, null);

    return chitchatService.getAllChitchats(languageId, level, dateFrom, dateTo);
  }

  @GetMapping("/{chitchatId}")
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
   * @param response             object that sets the locale.
   * @return response about the status of creating a new chat.
   */
  @PostMapping
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      @RequestBody ForCreateChitchatDto forCreateChitchatDto,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return ResponseEntity.ok(chitchatService
        .addChitchat(forCreateChitchatDto, CurrentUserService.getCurrentUsername()));
  }

  @PutMapping("{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> addUserToChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      @RequestParam("userId") Long userId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return chitchatService.addUserToChitchat(chitchatId, userId);
  }
}
