package com.group.chitchat.service.messagechat;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.model.ChatMessage;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.MessageUsers;
import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.model.User;
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
  public ChatMessage addMessage(MessageChatDto messageChatDto) {

    ChatMessage newMessageChat = messageChatRepo.save(getMessageChat(messageChatDto));

    Chitchat chitchat = chitchatRepo.findById(messageChatDto.getChitchatId())
        .orElseThrow(() -> new ChitchatsNotFoundException(messageChatDto.getChitchatId()));
    Set<User> usersInChitchat = chitchat.getUsersInChitchat();
    usersInChitchat.stream().iterator().forEachRemaining(
        user -> addMessageUsers(user, newMessageChat));

    return newMessageChat;
  }

  private void addMessageUsers(User user, ChatMessage message) {

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

    List<ChatMessage> messages = messageChatRepo.findAll();
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

    List<ChatMessage> messages = messageChatRepo.findAllByChitchatId(chitchatId);
    return messages.stream()
        .map(this::getMessageChatDto)
        .sorted(Comparator.comparing(MessageChatDto::getCreatedTime))
        .toList();
  }

  private ChatMessage getMessageChat(MessageChatDto messageChatDto) {

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

    return ChatMessage.builder()
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
  public MessageChatDto getMessageChatDto(ChatMessage messageChat) {
    return MessageChatDto.builder()
        .id(messageChat.getId())
        .authorName(messageChat.getAuthor().getUsername())
        .chitchatId(messageChat.getChitchat().getId())
        .message(messageChat.getMessage())
        .createdTime(messageChat.getCreatedTime().toString())
        .subscriptionType(Subscription.CHAT)
        .build();
  }
}
