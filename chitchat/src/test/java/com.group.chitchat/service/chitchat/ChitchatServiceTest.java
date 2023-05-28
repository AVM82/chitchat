package com.group.chitchat.service.chitchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.group.chitchat.exception.UserNotFoundException;
import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.Translation;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.TranslationRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.CalendarService;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.email.ReminderPlanner;
import com.group.chitchat.service.internationalization.BundlesService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ChitchatServiceTest {

  private static ChitchatRepo chitchatRepoMock;
  private static LanguageRepo languageRepoMock;
  private static UserRepo userRepoMock;
  private static CategoryRepo categoryRepoMock;
  private static TranslationRepo translationRepoMock;

  private static HttpServletRequest requestMock;

  private static EmailService emailServiceMock;
  private static ReminderPlanner reminderPlannerMock;
  private static BundlesService bundlesServiceMock;

  private static User user;
  private static Chitchat chitchat;
  private static Language language;
  private static Category category;
  private static Translation translationMessage;
  private static Translation translationTitle;
  private static ForCreateChitchatDto forCreateChitchatDto;
  private static StringBuffer chitchatUrl;

  private static ChitchatService chitchatService;

  @BeforeAll
  static void createInfrastructure() {
    user = TestEnvironment.createUser();
    chitchat = TestEnvironment.createChitchat(user);
    language = TestEnvironment.createLanguage();
    category = TestEnvironment.createCategory();
    translationMessage = TestEnvironment.createTranslationMessage();
    translationTitle = TestEnvironment.createTranslationTitle();
    forCreateChitchatDto = TestEnvironment.createForCreateChitchatDto();
    languageRepoMock = TestEnvironment.createMockLanguageRepo();
    userRepoMock = TestEnvironment.createMockUserRepo();
    categoryRepoMock = TestEnvironment.createMockCategoryRepo();
    translationRepoMock = TestEnvironment.createMockTranslationRepo();
    requestMock = TestEnvironment.createMockHttpServletRequest();
    bundlesServiceMock = TestEnvironment.createMockBundleService();
  }

  @BeforeEach
  void setUp() {
    emailServiceMock = TestEnvironment.createMockEmailService();
    reminderPlannerMock = TestEnvironment.createMockReminderPlanner();
    chitchatRepoMock = TestEnvironment.createMockChitchatRepo();
    chitchatService = new ChitchatService(chitchatRepoMock,
        userRepoMock, languageRepoMock, categoryRepoMock, reminderPlannerMock, translationRepoMock,
        bundlesServiceMock, emailServiceMock);

    chitchatUrl = new StringBuffer("testUrl");
    Mockito.when(requestMock.getRequestURL()).thenReturn(chitchatUrl);
    Mockito.when(userRepoMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    Mockito.when(languageRepoMock.findById(any())).thenReturn(Optional.of(language));
    Mockito.when(categoryRepoMock.findById(any())).thenReturn(Optional.of(category));
    Mockito.when(translationRepoMock.findByMessageKeyAndLocale(
        eq("email_confirm_create_chat"), any())).thenReturn(Optional.of(translationMessage));
    Mockito.when(translationRepoMock.findByMessageKeyAndLocale(
        eq("title_confirm_create"), any())).thenReturn(Optional.of(translationTitle));

  }

  @Test
  void testBodyTypeOfResponseWhenGetChitchat() {
    Mockito.when(chitchatRepoMock.findById(1L)).thenReturn(Optional.of(chitchat));
    ChitchatForResponseDto chitchatDto = ChitchatForResponseDto.builder().id(1L).build();
    assertEquals(chitchatDto.getClass(), chitchatService.getChitchat(1L).getClass());
    assertEquals(chitchatDto.getId(), chitchatService.getChitchat(1L).getId());
  }

  @Test
  void testConfirmationEmailAndMessage() {
    ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    chitchatService.addChitchat(forCreateChitchatDto, user.getUsername(), requestMock);

    verify(emailServiceMock, times(1))
        .sendEmail(emailCaptor.capture(), titleCaptor.capture(), messageCaptor.capture());

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu HH:mm");
    assertEquals("testEmail", emailCaptor.getValue());
    assertEquals(String.format(translationTitle.getMessage(), forCreateChitchatDto.getChatHeader()),
        titleCaptor.getValue());
    assertEquals(
        String.format(
            translationMessage.getMessage(),
            forCreateChitchatDto.getDate().format(formatter),
            category.getName(),
            language.getName(),
            forCreateChitchatDto.getLevel(),
            CalendarService.generateCalendarLink(forCreateChitchatDto.getChatHeader(),
                forCreateChitchatDto.getDescription(),
                forCreateChitchatDto.getDate(),
                chitchatUrl.toString() + "/chitchat?id=0"),
            chitchatUrl.toString() + "/chitchat?id=0"),
        messageCaptor.getValue());
  }

  @Test
  void shouldThrowExceptionWhenUserNotFound() {
    Mockito.when(userRepoMock.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

    Throwable exception = assertThrows(UserNotFoundException.class, () -> {
      chitchatService.addChitchat(forCreateChitchatDto, "notValidName", requestMock);
    });
    assertEquals(
        "Sorry but User with name notValidName doesn't exist in db!", exception.getMessage());
  }

  @Test
  void userShouldToAddToSetUserInChitchat() {
    ChitchatForResponseDto responseDto = chitchatService.addChitchat(
        forCreateChitchatDto, user.getUsername(), requestMock);

    Assertions.assertTrue(responseDto.getUsersInChitchat().contains(user.getUsername()));
  }

  @Test
  void shouldBeCreatedNewReminderWhenChitchatAdded() {
    chitchatService.addChitchat(forCreateChitchatDto, user.getUsername(), requestMock);
    ArgumentCaptor<Chitchat> chitchatCaptor = ArgumentCaptor.forClass(Chitchat.class);

    verify(reminderPlannerMock, times(1))
        .createReminderData(chitchatCaptor.capture());

    assertEquals(forCreateChitchatDto.getDate(), chitchatCaptor.getValue().getDate());
    assertEquals(user.getEmail(), chitchatCaptor.getValue().getAuthor().getEmail());
    assertEquals(forCreateChitchatDto.getLanguageId(),
        chitchatCaptor.getValue().getLanguage().getCodeIso());
  }

  @Test
  void checkInputAndOutputParametersWhenSaveCall() {
    chitchatService.addChitchat(forCreateChitchatDto, user.getUsername(), requestMock);
    ArgumentCaptor<Chitchat> chitchatCaptor = ArgumentCaptor.forClass(Chitchat.class);

    verify(chitchatRepoMock, times(1)).save(chitchatCaptor.capture());

    assertEquals(forCreateChitchatDto.getLevel(), chitchatCaptor.getValue().getLevel());
    assertEquals(forCreateChitchatDto.getCapacity(), chitchatCaptor.getValue().getCapacity());
    assertEquals(forCreateChitchatDto.getChatHeader(), chitchatCaptor.getValue().getChatName());
    assertEquals(forCreateChitchatDto.getLanguageId(),
        chitchatCaptor.getValue().getLanguage().getCodeIso());
    assertEquals(forCreateChitchatDto.getDescription(), chitchatCaptor.getValue().getDescription());
    assertEquals(forCreateChitchatDto.getCategoryId(),
        chitchatCaptor.getValue().getCategory().getId());
    assertEquals(forCreateChitchatDto.getDate(), chitchatCaptor.getValue().getDate());
    assertEquals(user.getUsername(), chitchatCaptor.getValue().getAuthor().getUsername());
  }
}
//
//  @Test
//  void getPageChitchats() {
//  }
//
//  @Test
//  void addChitchatLink() {
//  }
