package com.group.chitchat.service.chitchat;

import static org.mockito.Mockito.mock;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.Translation;
import com.group.chitchat.model.User;
import com.group.chitchat.model.UserData;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.TranslationRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.email.ReminderPlanner;
import com.group.chitchat.service.internationalization.BundlesService;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;

public class TestEnvironment {

  /**
   * Create user for test.
   *
   * @return user object.
   */
  public static User createUser() {
    return User.builder()
        .id(1L)
        .username("testName")
        .email("testEmail")
        .password("testPassword")
        .userData(new UserData())
        .build();
  }

  /**
   * Create chitchat for test.
   *
   * @param user User author for test chitchat.
   * @return chitchat object.
   */
  public static Chitchat createChitchat(User user) {
    return Chitchat.builder()
        .id(1L)
        .author(user)
        .chatName("test chat name")
        .description("test chat description")
        .date(LocalDateTime.now())
        .language(new Language())
        .category(new Category())
        .usersInChitchat(new HashSet<>())
        .build();
  }

  public static Language createLanguage() {
    return Language.builder().codeIso("en").name("english").build();
  }

  public static Category createCategory() {
    return Category.builder().id(1).name("test category").build();
  }

  /**
   * Create translation object with message for test.
   *
   * @return Translation object.
   */
  public static Translation createTranslationMessage() {
    return Translation.builder()
        .locale(Locale.ENGLISH)
        .messageKey("email_confirm_create_chat")
        .message("test message: %s %s %s %s %s %s")
        .build();
  }

  /**
   * Create translation object with title for test.
   *
   * @return Translation object.
   */
  public static Translation createTranslationTitle() {
    return Translation.builder()
        .locale(Locale.ENGLISH)
        .messageKey("title_confirm_create")
        .message("test title: %s")
        .build();
  }

  /**
   * Create dto for test.
   *
   * @return ForCreateChitchatDto object.
   */
  public static ForCreateChitchatDto createForCreateChitchatDto() {
    return ForCreateChitchatDto.builder()
        .date(LocalDateTime.now())
        .chatHeader("test chat name")
        .description("test chat description")
        .build();
  }

  public static ChitchatRepo createMockChitchatRepo() {
    return mock(ChitchatRepo.class);
  }

  public static LanguageRepo createMockLanguageRepo() {
    return mock(LanguageRepo.class);
  }

  public static UserRepo createMockUserRepo() {
    return mock(UserRepo.class);
  }

  public static CategoryRepo createMockCategoryRepo() {
    return mock(CategoryRepo.class);
  }

  public static TranslationRepo createMockTranslationRepo() {
    return mock(TranslationRepo.class);
  }

  public static EmailService createMockEmailService() {
    return mock(EmailService.class);
  }

  public static ReminderPlanner createMockReminderPlanner() {
    return mock(ReminderPlanner.class);
  }

  public static BundlesService createMockBundleService() {
    return mock(BundlesService.class);
  }

  public static HttpServletRequest createMockHttpServletRequest() {
    return mock(HttpServletRequest.class);
  }
}
