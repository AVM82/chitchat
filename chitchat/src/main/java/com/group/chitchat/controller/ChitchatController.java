package com.group.chitchat.controller;

import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ChitchatUnreadCountDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.model.dto.MessageChatDto;
import com.group.chitchat.model.dto.SimpleDataDto;
import com.group.chitchat.service.chitchat.ChitchatService;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.messagechat.MessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
  private final MessageService messageService;

  /**
   * Takes a list of all chats.
   *
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return list of all chats.
   */
  @GetMapping("/all")
  public ResponseEntity<Page<ChitchatForResponseDto>> getAllChitchats(
      @PageableDefault(size = 10, page = 0, sort = "date") Pageable pageable,
      @RequestParam(value = "languageId", required = false) String languageId,
      @RequestParam(value = "levelId", required = false) String level,
      @RequestParam(value = "dateFrom", required = false) String dateFrom,
      @RequestParam(value = "dateTo", required = false) String dateTo,
      @RequestParam(value = "categoryId", required = false) Integer categoryId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        chitchatService.getAllChitchats(languageId, level, dateFrom, dateTo, categoryId, pageable));
  }

  /**
   * Returns chitchat by id. Closed end-point.
   *
   * @param chitchatId incoming chitchat id.
   * @return response with status and chitchat by id.
   */
  @GetMapping("/{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> getChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(chitchatService.getChitchat(chitchatId));
  }

  /**
   * Returns chitchat by id. Opened end-point.
   *
   * @param chitchatId incoming chitchat id.
   * @return response with status and chitchat by id.
   */
  @GetMapping("/all/{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> getPublicChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(chitchatService.getChitchat(chitchatId));
  }

  /**
   * Get all messages by chitchat id.
   *
   * @param chitchatId A chitchat id
   * @return Response with status and list of chitchat messages.
   */
  @GetMapping("/chat_messages/{chitchatId}")
  public ResponseEntity<List<MessageChatDto>> getChitchatAllMessages(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(messageService.getAllMessagesByChitchatId(chitchatId));
  }

  /**
   * Get unread user messages.
   *
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return List of unread user messages.
   */
  @GetMapping("/chat_messages/unread_messages")
  public ResponseEntity<List<MessageUsers>> getUnreadUserMessages(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        messageService.getAllUnreadUserMessages(requestHeader.getUserPrincipal().getName()));
  }

  /**
   * Get count of unread user messages.
   *
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return Count of unread user messages.
   */
  @GetMapping("/chat_messages/unread_count")
  public ResponseEntity<SimpleDataDto<Long>> getTotalCountUnreadUserMessages(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        new SimpleDataDto<Long>(
            messageService
                .getTotalCountUnreadUserMessages(requestHeader.getUserPrincipal().getName())));
  }

  /**
   * Get unread user chitchats with count.
   *
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   * @return List of unread user chitchats with count.
   */
  @GetMapping("/chat_messages/unread_chitchats")
  public ResponseEntity<List<ChitchatUnreadCountDto>> getAllUnreadUserChitchats(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        messageService.getAllUnreadUserChitchats(
            requestHeader.getUserPrincipal().getName()));
  }

  /**
   * Marks as read user messages of chitchat.
   *
   * @param chitchatId    A chitchatId
   * @param date          A date
   * @param requestHeader An object for obtaining request header parameters.
   * @param response      object that sets the locale.
   */
  @PutMapping("/chat_messages/{chitchatId}")
  public void markReadMessagesOfChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      @RequestParam(value = "date") String date,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    messageService.markAsReadUserMessagesOfChitchat(chitchatId,
        requestHeader.getUserPrincipal().getName(), date);
  }

  /**
   * Creates and adds a new chat.
   *
   * @param forCreateChitchatDto An object that contains the necessary data to create a chat.
   * @param requestHeader        An object for obtaining request header parameters.
   * @param response             object that sets the locale.
   * @return response about the status of creating a new chat.
   */
  @PostMapping()
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      @RequestBody ForCreateChitchatDto forCreateChitchatDto,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        chitchatService.addChitchat(
            forCreateChitchatDto, requestHeader.getUserPrincipal().getName(), requestHeader));
  }

  /**
   * Add user by id to chitchat by id.
   *
   * @param chitchatId    id of chitchat.
   * @param requestHeader header.
   * @param response      Servlet response.
   * @return Response with chitchat.
   */
  @PutMapping("/{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> addUserToChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        chitchatService.addUserToChitchat(chitchatId, requestHeader));
  }

  /**
   * Removes user from chitchat.
   *
   * @param chitchatId    current chitchat id.
   * @param requestHeader header.
   * @param response      response.
   * @return response with body of updated chitchat.
   */
  @PutMapping("/{chitchatId}")
  public ResponseEntity<ChitchatForResponseDto> removeUserFromChitchat(
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(
        chitchatService.removeUserFromChitchat(
            chitchatId, requestHeader.getUserPrincipal().getName()));
  }

  /**
   * Save link to videoconference.
   *
   * @param chitchatId current chitchat.
   * @param simpleDto  dto with value of link.
   * @return Response with status and conference link.
   */
  @PutMapping("/link/{chitchatId}")
  public ResponseEntity<SimpleDataDto<String>> addChitchatLink(
      @RequestBody SimpleDataDto<String> simpleDto,
      @PathVariable("chitchatId") Long chitchatId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return ResponseEntity.ok(chitchatService.addChitchatLink(chitchatId, simpleDto));
  }
}
