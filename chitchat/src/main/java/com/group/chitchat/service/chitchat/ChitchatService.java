package com.group.chitchat.service.chitchat;

import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.CalendarService;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.internationalization.ResourcesBundleService;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ChitchatService {

  private final ChitchatRepo chitchatRepo;
  private final UserRepo userRepo;
  private final LanguageRepo languageRepo;
  private final CategoryRepo categoryRepo;
  /**
   * Messages for sending email.
   */
  private static final String MESSAGE_CONFIRM_CREATE =
      "You created a new chitchat. Follow the link to add it to Google Calendar: \n";

  private static final String MESSAGE_PARTICIPATION_CREATE = "You joined to chitchat";
  private final EmailService emailService;
  private final ResourcesBundleService resourceBundleService;

  /**
   * Returns list of chitchats.
   *
   * @param chitchatId incoming chitchat id.
   * @return list of chitchats.
   */
  public ResponseEntity<ChitchatForResponseDto> getChitchat(Long chitchatId) {
    Optional<Chitchat> chitchatOptional = chitchatRepo.findById(chitchatId);
    if (chitchatOptional.isEmpty()) {
      throw new NoSuchElementException(String.format("Chitchat with id %s not found", chitchatId));
    } else {
      HttpHeaders header = new HttpHeaders();
      header.set("Access-Control-Allow-Origin", "*");
      return ResponseEntity.status(HttpStatus.OK).headers(header).body(
          ChitchatDtoService.getFromEntity(chitchatOptional.get()));
    }
  }

  /**
   * Creating a new chitchat and saving into DB.
   *
   * @param chitchatDto Dto for create chitchat.
   * @return response with info of chitchat.
   */
  public ChitchatForResponseDto addChitchat(ForCreateChitchatDto chitchatDto, String authorName) {
    User author = userRepo.findByUsername(authorName)
        .orElseThrow(() -> new UsernameNotFoundException(authorName));

    Language language = languageRepo.findById(chitchatDto.getLanguageId()).orElseThrow();
    Category category = categoryRepo.findById(chitchatDto.getCategoryId()).orElseThrow();
    Chitchat chitchat = ChitchatDtoService.getFromDtoForCreate(
        chitchatDto, author, language, category);

    //TODO add exceptions for two throws below

    chitchat.getUsersInChitchat().add(author);

    chitchatRepo.save(chitchat);

    sendEmail(chitchat, MESSAGE_CONFIRM_CREATE);

    return ChitchatDtoService.getFromEntity(chitchat);
  }

  /**
   * Add user by id to chitchat by id.
   *
   * @param chitchatId chitchat id.
   * @param userId     user id.
   * @return chitchatDto for response
   */
  @Transactional
  public ResponseEntity<ChitchatForResponseDto> addUserToChitchat(Long chitchatId, Long userId) {
    Optional<Chitchat> chitchatOptional = chitchatRepo.findById(chitchatId);
    Optional<User> userOptional = userRepo.findById(userId);

    if (chitchatOptional.isEmpty() || userOptional.isEmpty()) {
      throw new NoSuchElementException(String.format(
          "Chitchat with id %s or user with id %s not found", chitchatId, userId));
    }
    Chitchat chitchat = chitchatOptional.get();
    User user = userOptional.get();

    if (chitchat.getUsersInChitchat().contains(user)) {
      throw new UserAlreadyExistException("user is already in this chitchat");
    }
    Set<User> usersInChitchat = chitchat.getUsersInChitchat();
    usersInChitchat.add(user);
    chitchat.setUsersInChitchat(usersInChitchat);

    return ResponseEntity.ok(
        ChitchatDtoService.getFromEntity(chitchat));
  }

  /**
   * Return all chitchats by parameters.
   *
   * @param languageId Incoming languageId.
   * @param level      Incoming level of language.
   * @param dateFrom   Date of start;
   * @param dateTo     Date of end;
   * @return Chitchats by parameters.
   */
  public ResponseEntity<List<ChitchatForResponseDto>> getAllChitchats(String languageId,
      String level, LocalDateTime dateFrom, LocalDateTime dateTo) {

    List<Chitchat> chitchats;
    if (languageId != null) {
      Language language = languageRepo.findById(languageId).orElseThrow();
      chitchats = chitchatRepo.findAllByLanguage(language);
    } else {
      chitchats = chitchatRepo.findAll();
    }
    if (level != null) {
      chitchats = chitchats.stream().filter(chitchat -> chitchat.getLevel().name().equals(level))
          .toList();
    }
    HttpHeaders header = new HttpHeaders();
    header.set("Access-Control-Allow-Origin", "*");
    return ResponseEntity.status(HttpStatus.OK).headers(header)
        .body(chitchatFiltration(chitchats, dateFrom, dateTo));

  }

  private List<ChitchatForResponseDto> chitchatFiltration(List<Chitchat> chitchats,
      LocalDateTime dateFrom,
      LocalDateTime dateTo) {

    return chitchats.stream()
        .filter(chitchat -> {
          if (dateFrom != null) {
            return chitchat.getDate().isAfter(dateFrom);
          } else {
            return true;
          }
        })
        .filter(chitchat -> {
          if (dateTo != null) {
            return chitchat.getDate().isBefore(dateTo);
          } else {
            return true;
          }
        })
        .filter(chitchat -> chitchat.getDate().isAfter(LocalDateTime.now()))
        .sorted(Comparator.comparing(Chitchat::getDate))
        .map(ChitchatDtoService::getFromEntity).toList();
  }

  /**
   * Sends a confirmation email.
   *
   * @param chitchat Entity with data.
   * @param message  Message for sending
   */
  private void sendEmail(Chitchat chitchat, String message) {
    emailService.sendEmail(
        chitchat.getAuthor().getEmail(),
        String.format("New chitchat: %s", chitchat.getChatName()),
        message + CalendarService.generateCalendarLink(
            chitchat.getChatName(),
            chitchat.getDescription(),
            chitchat.getDate())
    );
  }
}
