package com.group.chitchat.service.chitchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.Translation;
import com.group.chitchat.model.User;
import com.group.chitchat.model.UserData;
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
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ChitchatServiceTest {

  private final ChitchatRepo chitchatRepo = mock(ChitchatRepo.class);
  private final LanguageRepo languageRepo = mock(LanguageRepo.class);
  private final UserRepo userRepo = mock(UserRepo.class);
  private final CategoryRepo categoryRepo = mock(CategoryRepo.class);
  private final ReminderPlanner reminderPlanner = mock(ReminderPlanner.class);
  private final TranslationRepo translationRepo = mock(TranslationRepo.class);
  private BundlesService bundlesService;
  private final EmailService emailService = mock(EmailService.class);
  private ChitchatService chitchatService;
  private Chitchat chitchat;
  private User user;
  private Language language;
  private Category category;
  private Translation translation;
  private ForCreateChitchatDto forCreateChitchatDto;

  ChitchatServiceTest() {
  }

  @BeforeAll
  @BeforeEach
  void setUp() {
    user = User.builder()
        .id(1L)
        .username("testName")
        .email("testEmail")
        .password("testPassword")
        .userData(new UserData())
        .build();
    chitchat = Chitchat.builder()
        .id(1L)
        .author(user)
        .chatName("test chat name")
        .description("test chat description")
        .date(LocalDateTime.now())
        .language(new Language())
        .category(new Category())
        .usersInChitchat(new HashSet<>())
        .build();
    chitchatService = new ChitchatService(
        chitchatRepo,
        userRepo,
        languageRepo,
        categoryRepo,
        reminderPlanner,
        translationRepo,
        bundlesService,
        emailService);
    language = Language.builder().codeIso("en").name("english").build();
    category = Category.builder().id(1).name("test category").build();
    translation = Translation.builder()
        .locale(Locale.ENGLISH)
        .key("email_confirm_create_chat")
        .message("test message: %s %s %s %s %s %s")
        .build();
    forCreateChitchatDto = ForCreateChitchatDto.builder()
        .date(LocalDateTime.now())
        .chatHeader("test chat name")
        .description("test chat description")
        .build();
  }

  @Test
  void getChitchat() {
    Mockito.when(chitchatRepo.findById(1L)).thenReturn(Optional.of(chitchat));
    ChitchatForResponseDto chitchatDto = ChitchatForResponseDto.builder().id(1L).build();
    assertEquals(chitchatDto.getClass(), chitchatService.getChitchat(1L).getClass());
    assertEquals(chitchatDto.getId(), chitchatService.getChitchat(1L).getId());

  }

  @Test
  void addChitchat() {
    StringBuffer url = new StringBuffer("testUrl");
    HttpServletRequest request = mock(HttpServletRequest.class);
    Mockito.when(request.getRequestURL()).thenReturn(url);
    Mockito.when(userRepo.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
    Mockito.when(languageRepo.findById(any())).thenReturn(Optional.of(language));
    Mockito.when(categoryRepo.findById(any())).thenReturn(Optional.of(category));
    Mockito.when(translationRepo.findByKeyAndLocale(any(), any()))
        .thenReturn(Optional.of(translation));

//    doNothing().when(reminderPlanner).createReminderData(any());

    ArgumentCaptor<String> emailCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> titleCaptor = ArgumentCaptor.forClass(String.class);
    ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);

    chitchatService.addChitchat(forCreateChitchatDto, user.getUsername(), request);

    verify(emailService, times(1))
        .sendEmail(emailCaptor.capture(), titleCaptor.capture(), messageCaptor.capture());
//
    assertEquals("testEmail", emailCaptor.getValue());
    assertEquals("Chitchat: " + forCreateChitchatDto.getChatHeader(), titleCaptor.getValue());
    assertEquals(String.format(translation.getMessage(),
            forCreateChitchatDto.getDate(),
            category.getName(),
            language.getName(),
            forCreateChitchatDto.getLevel(),
            CalendarService.generateCalendarLink(forCreateChitchatDto.getChatHeader(),
                forCreateChitchatDto.getDescription(),
                forCreateChitchatDto.getDate(),
                url.toString()),
            url.toString()),
        messageCaptor.getValue());
  }

  @Test
  void addUserToChitchat() {
  }

  @Test
  void getPageChitchats() {
  }

  @Test
  void addChitchatLink() {
  }
}