package com.group.chitchat.service.chitchat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.UserData;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.TranslationRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.EmailService;
import com.group.chitchat.service.email.ReminderPlanner;
import com.group.chitchat.service.internationalization.BundlesService;
import java.util.HashSet;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ChitchatServiceTest {

  private final ChitchatRepo chitchatRepo = mock(ChitchatRepo.class);
  private final LanguageRepo languageRepo = mock(LanguageRepo.class);
  private UserRepo userRepo;
  private CategoryRepo categoryRepo;
  private ReminderPlanner reminderPlanner;
  private TranslationRepo translationRepo;
  private BundlesService bundlesService;
  private EmailService emailService;
  private ChitchatService chitchatService;
  private Chitchat chitchat;

  ChitchatServiceTest() {
  }


  @BeforeEach
  void setUp() {
    User user = User.builder()
        .id(1L)
        .username("testName")
        .email("testEmail")
        .password("testPassword")
        .userData(new UserData())
        .build();
    chitchat = Chitchat.builder()
        .id(1L)
        .author(user)
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
  }

  @Test
  void getChitchat() {
    Mockito.when(chitchatRepo.findById(1L)).thenReturn(Optional.of(chitchat));
    ChitchatForResponseDto chitchatDto = ChitchatForResponseDto.builder().id(1L).build();
    assertEquals(chitchatDto, chitchatService.getChitchat(1L));

  }

  @Test
  void addChitchat() {
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