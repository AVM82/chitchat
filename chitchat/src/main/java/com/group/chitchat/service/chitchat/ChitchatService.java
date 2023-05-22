package com.group.chitchat.service.chitchat;

import static org.springframework.data.jpa.domain.Specification.where;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.exception.UserAlreadyExistException;
import com.group.chitchat.exception.UserNotFoundException;
import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.model.dto.SimpleDataDto;
import com.group.chitchat.model.enums.Levels;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.CalendarService;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.email.ReminderPlanner;
import com.group.chitchat.service.internationalization.BundlesService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Log4j2
public class ChitchatService {

  private final ChitchatRepo chitchatRepo;
  private final UserRepo userRepo;
  private final LanguageRepo languageRepo;
  private final CategoryRepo categoryRepo;
  private final ReminderPlanner reminderPlanner;


  /**
   * Messages for sending email.
   */
  private static final String CONFIRM_CREATE_MESSAGE = "m.create_chitchat";
  private final BundlesService bundlesService;

  private static final String CONFIRM_PARTICIPATION_MESSAGE = "m.participate_chitchat";
  private final EmailService emailService;

  /**
   * Returns list of chitchats.
   *
   * @param chitchatId incoming chitchat id.
   * @return list of chitchats.
   */
  public ResponseEntity<ChitchatForResponseDto> getChitchat(Long chitchatId) {
    Optional<Chitchat> chitchatOptional = chitchatRepo.findById(chitchatId);
    if (chitchatOptional.isEmpty()) {
      throw new ChitchatsNotFoundException(chitchatId);
    } else {
      return ResponseEntity.ok(ChitchatDtoService.getFromEntity(chitchatOptional.get()));
    }
  }

  /**
   * Creating a new chitchat and saving into DB.
   *
   * @param chitchatDto Dto for create chitchat.
   * @return response with info of chitchat.
   */
  public ResponseEntity<ChitchatForResponseDto> addChitchat(
      ForCreateChitchatDto chitchatDto, String authorName, HttpServletRequest request) {

    User author = userRepo.findByUsername(authorName)
        .orElseThrow(() -> new UserNotFoundException(authorName));
    Language language = languageRepo.findById(chitchatDto.getLanguageId()).orElseThrow();
    Category category = categoryRepo.findById(chitchatDto.getCategoryId()).orElseThrow();
    Chitchat chitchat = ChitchatDtoService.getFromDtoForCreate(
        chitchatDto, author, language, category);

    //TODO add exceptions for two throws below

    chitchat.getUsersInChitchat().add(author);
    reminderPlanner.createReminderData(chitchat);
    chitchatRepo.save(chitchat);
    log.info("New Chitchat has been saved");

    String url = request.getRequestURL().toString().replace("/api/v1/chitchats", "")
        + "/chitchat?id=" + chitchat.getId();

    sendConfirmEmail(author.getEmail(), chitchat, url);

    return ResponseEntity.ok(ChitchatDtoService.getFromEntity(chitchat));
  }

  private void sendConfirmEmail(String email, Chitchat chitchat, String url) {
    log.info("create message");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");
    String greeting = String.format(bundlesService.getMessForLocale(
            "email.confirm_greeting", Locale.getDefault()),
        chitchat.getDate().format(formatter),
        chitchat.getCategory().getName(),
        chitchat.getLanguage().getName(),
        chitchat.getLevel());
    String step1 = String.format(bundlesService.getMessForLocale(
            "email.confirm_step_1", Locale.getDefault()),
        CalendarService.generateCalendarLink(
            chitchat.getChatName(),
            chitchat.getDescription(),
            chitchat.getDate(),
            url));
    String step2 = bundlesService.getMessForLocale(
        "email.confirm_step_2", Locale.getDefault());
    String step3 = bundlesService.getMessForLocale(
        "email.confirm_step_3", Locale.getDefault());
    String step4 = bundlesService.getMessForLocale(
        "email.confirm_step_4", Locale.getDefault());
    String step5 = String.format(bundlesService.getMessForLocale(
        "email.confirm_step_5", Locale.getDefault()), url);
    String note1 = bundlesService.getMessForLocale(
        "email.confirm_notification_1", Locale.getDefault());
    String note2 = bundlesService.getMessForLocale(
        "email.confirm_notification_2", Locale.getDefault());
    String note3 = bundlesService.getMessForLocale(
        "email.confirm_notification_3", Locale.getDefault());

    String message = greeting + step1 + step2 + step3 + step4 + step5 + note1 + note2 + note3;

    emailService.sendEmail(email, "Chitchat: " + chitchat.getChatName(), message);
  }

  /**
   * Add user by id to chitchat by id.
   *
   * @param chitchatId chitchat id.
   * @param userId     user id.
   * @return chitchatDto for response
   */
  @Transactional
  public ResponseEntity<ChitchatForResponseDto> addUserToChitchat(Long chitchatId, Long userId,
      HttpServletRequest request) {
    Chitchat chitchat = chitchatRepo.findById(chitchatId)
        .orElseThrow(() -> new ChitchatsNotFoundException(chitchatId));

    User user = userRepo.findById(userId)
        .orElseThrow(() -> new UserNotFoundException(userId));

    if (chitchat.getUsersInChitchat().contains(user)) {
      throw new UserAlreadyExistException(userId);
    }
    Set<User> usersInChitchat = chitchat.getUsersInChitchat();
    usersInChitchat.add(user);
    chitchat.setUsersInChitchat(usersInChitchat);

    Set<String> userEmails = chitchat.getRemindersData().getEmails();
    userEmails.add(user.getEmail());
    chitchat.getRemindersData().setEmails(userEmails);

    String url = request.getRequestURL().toString()
        .replace("/api/v1/chitchats/" + chitchatId, "")
        + "/chitchat?id=" + chitchatId;

    sendEmail(user.getEmail(), chitchat, String.format(bundlesService.getMessForLocale(
        CONFIRM_PARTICIPATION_MESSAGE, Locale.getDefault()), url), url);

    return ResponseEntity.ok(
        ChitchatDtoService.getFromEntity(chitchat));
  }

  /**
   * Return all chitchats by parameters.
   *
   * @param languageId  Incoming languageId.
   * @param levelStr    Incoming level of language.
   * @param dateFromStr Date of start;
   * @param dateToStr   Date of end;
   * @param categoryId  Category id.
   * @param pageable    Information for pagination.
   * @return Page by parameters.
   */
  public ResponseEntity<Page<ChitchatForResponseDto>> getPageChitchats(
      String languageId, String levelStr, String dateFromStr, String dateToStr,
      Integer categoryId, Pageable pageable) {

    Specification<Chitchat> specification = where(dateFromSpecification(LocalDateTime.now()));

    if (categoryId != null) {
      Category category = categoryRepo.findById(categoryId).orElseThrow();
      specification = where(categorySpecification(category)).and(specification);
    }
    if (languageId != null && !languageId.isEmpty()) {
      Language language = languageRepo.findById(languageId).orElseThrow();
      specification = where(languageSpecification(language)).and(specification);
    }
    if (levelStr != null && !levelStr.isEmpty()) {
      Levels level = Levels.valueOf(levelStr);
      specification = where(levelSpecification(level)).and(specification);
    }
    if (dateFromStr != null && !dateFromStr.isEmpty()) {
      LocalDateTime dateFrom = LocalDate.parse(dateFromStr).atStartOfDay();
      specification = where(dateFromSpecification(dateFrom)).and(specification);
    }
    if (dateToStr != null && !dateToStr.isEmpty()) {
      LocalDateTime dateTo = LocalDate.parse(dateToStr).atTime(LocalTime.MAX);
      specification = where(dateToSpecification(dateTo)).and(specification);
    }
    Page<Chitchat> page = chitchatRepo.findAll(specification, pageable);

    return ResponseEntity.ok(page
        .map(ChitchatDtoService::getFromEntity));
  }

  /**
   * Sends a confirmation email.
   *
   * @param emailAddress of user.
   * @param chitchat     Entity with data.
   * @param message      Message for sending.
   * @param url          of new chitchat.
   */
  private void sendEmail(String emailAddress, Chitchat chitchat, String message, String url) {
    emailService.sendEmail(
        emailAddress,
        String.format("Chitchat: %s", chitchat.getChatName()),
        message + CalendarService.generateCalendarLink(
            chitchat.getChatName(),
            chitchat.getDescription(),
            chitchat.getDate(),
            url)
    );
  }

  private Specification<Chitchat> languageSpecification(Language language) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get("language"), language);
  }

  private Specification<Chitchat> levelSpecification(Levels level) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get("level"), level);
  }

  private Specification<Chitchat> categorySpecification(Category category) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.equal(root.get("category"), category);
  }

  private Specification<Chitchat> dateFromSpecification(LocalDateTime date) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.greaterThanOrEqualTo(root.get("date"), date);
  }

  private Specification<Chitchat> dateToSpecification(LocalDateTime date) {
    return (root, query, criteriaBuilder)
        -> criteriaBuilder.lessThanOrEqualTo(root.get("date"), date);
  }

  /**
   * Save link to videoconference.
   *
   * @param chitchatId current chitchat
   * @param simpleDto  dto with value of link
   * @return response with link
   */
  @Transactional
  public ResponseEntity<SimpleDataDto<String>> addChitchatLink(Long chitchatId,
      SimpleDataDto<String> simpleDto) {
    Chitchat chitchat = chitchatRepo.findById(chitchatId)
        .orElseThrow(() -> new ChitchatsNotFoundException(chitchatId));
    chitchat.setConferenceLink(simpleDto.getValue());
    chitchat.getRemindersData().setLink(simpleDto.getValue());
    return ResponseEntity.ok(simpleDto);
  }
}
