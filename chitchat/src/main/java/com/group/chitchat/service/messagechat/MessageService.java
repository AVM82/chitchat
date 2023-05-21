package com.group.chitchat.service.messagechat;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.MessageChat;
import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatUnreadCountDto;
import com.group.chitchat.model.dto.MessageChatDto;
import com.group.chitchat.model.enums.Subscription;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.MessageChatRepo;
import com.group.chitchat.repository.MessageUsersRepo;
import com.group.chitchat.repository.UserRepo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@AllArgsConstructor
public class MessageService {

  private final MessageChatRepo messageChatRepo;
  private final MessageUsersRepo messageUsersRepo;
  private final UserRepo userRepo;
  private final ChitchatRepo chitchatRepo;

  /**
   * Add one message to the database.
   *
   * @param messageChatDto Dto that contains all message attributes.
   * @return status and message entity object.
   */
  @Transactional
  public MessageChat addMessage(MessageChatDto messageChatDto) {

    MessageChat newMessageChat = messageChatRepo.save(getMessageChat(messageChatDto));

    Chitchat chitchat = chitchatRepo.findById(messageChatDto.getChitchatId())
        .orElseThrow(() -> new ChitchatsNotFoundException(messageChatDto.getChitchatId()));
    Set<User> usersInChitchat = chitchat.getUsersInChitchat();
    usersInChitchat.stream().iterator().forEachRemaining(
        user -> addMessageUsers(user, newMessageChat));

    return newMessageChat;
  }

  private void addMessageUsers(User user, MessageChat message) {

    MessageUsers messageUsers = MessageUsers.builder()
        .id(new MessageUsersKey(message.getId(), user.getId()))
        .user(user)
        .messages(message)
        .readStatus(false)
        .build();
    messageUsersRepo.save(messageUsers);
  }

  /**
   * Get all messages from the database.
   *
   * @return list of all messages.
   */
  public List<MessageChatDto> getAllMessages() {

    List<MessageChat> messages = messageChatRepo.findAll();
    return messages.stream()
        .map(this::getMessageChatDto)
        .sorted(Comparator.comparing(MessageChatDto::getCreatedTime))
        .toList();
  }

  /**
   * Get all messages by chitchat id.
   *
   * @param chitchatId A chitchat id
   * @return list of chitchat messages.
   */
  public List<MessageChatDto> getAllMessagesByChitchatId(Long chitchatId) {

    List<MessageChat> messages = messageChatRepo.findAllByChitchatId(chitchatId);
    return messages.stream()
        .map(this::getMessageChatDto)
        .sorted(Comparator.comparing(MessageChatDto::getCreatedTime))
        .toList();
  }

  private MessageChat getMessageChat(MessageChatDto messageChatDto) {

    User author = new User();
    Chitchat chitchat = new Chitchat();

    Optional<User> optionalUser = userRepo.findByUsername(messageChatDto.getAuthorName());
    Optional<Chitchat> optionalChitchat = chitchatRepo.findById(messageChatDto.getChitchatId());
    if (optionalUser.isPresent()) {
      author = optionalUser.get();
    }
    if (optionalChitchat.isPresent()) {
      chitchat = optionalChitchat.get();
    }

    LocalDateTime parseCreatedTime;
    try {
      parseCreatedTime = LocalDateTime.parse(messageChatDto.getCreatedTime(),
          DateTimeFormatter.ISO_DATE_TIME);
    } catch (Exception e) {
      parseCreatedTime = LocalDateTime.now();
      log.info("Wrong json date format {}", messageChatDto.getCreatedTime());
      log.info("Set LocalDateTime.now(): {}", parseCreatedTime);
    }

    return MessageChat.builder()
        .author(author)
        .chitchat(chitchat)
        .message(messageChatDto.getMessage())
        .createdTime(parseCreatedTime)
        .subscriptionType(messageChatDto.getSubscriptionType())
        .build();
  }

  /**
   * Map MessageChat to MessageChatDto.
   *
   * @param messageChat A MessageChat object
   * @return MessageChatDto
   */
  public MessageChatDto getMessageChatDto(MessageChat messageChat) {
    return MessageChatDto.builder()
        .id(messageChat.getId())
        .authorName(messageChat.getAuthor().getUsername())
        .chitchatId(messageChat.getChitchat().getId())
        .message(messageChat.getMessage())
        .createdTime(messageChat.getCreatedTime().toString())
        .subscriptionType(Subscription.CHAT)
        .build();
  }

  /**
   * Get get all unread user messages.
   *
   * @param userName User name
   * @return List of unread user messages
   */
  public List<MessageUsers> getAllUnreadUserMessages(String userName) {
    return messageUsersRepo.findAllByUserUsernameAndReadStatusEquals(userName, false);
  }

  public List<ChitchatUnreadCountDto> getAllUnreadUserChitchats(
      String userName) {
    return messageUsersRepo.findAllUnreadUserChitchats(userName);
  }

  /**
   * Get total count of unread user messages.
   *
   * @param userName User name
   * @return Total count
   */
  public Long getTotalCountUnreadUserMessages(String userName) {
    return messageUsersRepo.totalCountUnreadUserMessages(userName);
  }

  /**
   * Marks as read user messages of chitchat.
   *
   * @param chitchatId A chitchatId
   * @param userName   A userName
   * @param date       A date
   */
  @Transactional
  public void markAsReadUserMessagesOfChitchat(Long chitchatId,
      String userName, String date) {
    LocalDateTime parseDateTime;
    try {
      parseDateTime = LocalDateTime.parse(date,
          DateTimeFormatter.ISO_DATE_TIME);
    } catch (Exception e) {
      parseDateTime = LocalDateTime.now();
      log.info("Wrong json date format {}", date);
      log.info("Set LocalDateTime.now(): {}", parseDateTime);
    }
    LocalDateTime finalParseDateTime = parseDateTime;
    messageUsersRepo.findAllByUserUsernameAndReadStatusEquals(userName, false).stream()
        .filter(messageUsers -> messageUsers.getMessages().getChitchat().getId() == chitchatId
            && messageUsers.getMessages().getCreatedTime().isBefore(finalParseDateTime))
        .forEach(messageUsers -> {
          messageUsers.setReadStatus(true);
          messageUsersRepo.save(messageUsers);
        });
    //TODO:
    // messageUsersRepo.setMarkAsReadUserMessagesOfChitchat(chitchatId, userName, parseDateTime);
  }
}
