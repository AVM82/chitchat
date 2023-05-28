package com.group.chitchat.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.model.Translation;
import com.group.chitchat.repository.MessageUsersRepo;
import com.group.chitchat.repository.TranslationRepo;
import com.group.chitchat.service.email.ChitchatEmailService;
import com.group.chitchat.service.profile.StorageServiceS3Bucket;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


@DirtiesContext
@SpringBootTest(classes = ChitchatController.class)
@AutoConfigureMockMvc
@ComponentScan("com.group.chitchat")
class ChitchatControllerTest {

  @Autowired
  MockMvc mockMvc;


  @MockBean
  StorageServiceS3Bucket storageServiceS3Bucket;
  @MockBean
  ChitchatEmailService chitchatEmailService;
  @MockBean
  TranslationRepo translationRepo;

  /**
   * Checks the performance of the controller.
   */
  @Test
  void contextLoadsSmokeTest(@Autowired ChitchatController controller) {
    assertThat(controller).isNotNull();
  }

  /**
   * Checks the transaction status of receiving all chitchats.
   */
  @Test
  @WithMockUser("testUser2")
  void getAllChitchatsTestStatus() throws Exception {

    this.mockMvc.perform(
            get("/api/v1/chitchats/all"))
        .andDo(print())
        .andExpect(status().isOk());
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.chitchats":"id"=2, "t.users":"username"="testUser1",
   * "t.chitchat_users":("chitchat_id"=2,
   * "user_id"=2), "t.languages": "id"="deutsch", "t.categories":"name"="Casual Conversation",
   * "t.user_data":"user_id"=2.
   */
  @Test
  @WithMockUser("testUser2")
  void getChitchatTest() throws Exception {

    String expectedResult = "{\"id\":2,\"chatName\":\"lets go\",\"authorName\":\"testUser1\","
        + "\"categoryName\":\"Casual Conversation\",\"description\":\"speak with me\","
        + "\"languageName\":\"deutsch\",\"level\":\"NATIVE\",\"capacity\":2,"
        + "\"date\":\"2023-06-01T12:00:00\",\"usersInChitchat\":[\"testUser1\"],"
        + "\"avatarUrl\":null,\"conferenceLink\":null}";

    this.mockMvc.perform(
            get("/api/v1/chitchats/2")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }

  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.chitchats":"id"=1, "t.users":"username"="testUser2", "t.chitchat_users":("chitchat_id"=1,
   * "user_id"=1), "t.languages":"id"="en", "t.user_data":"user_id"=1.
   */
  @Test
  @WithMockUser("testUserUser")
  void getPublicChitchatTest() throws Exception {

    String expectedResult = "{\"id\":1,\"chatName\":\"test header\",\"authorName\":\"testUser2\","
        + "\"categoryName\":\"Computer Science and IT\",\"description\":\"test description\","
        + "\"languageName\":\"english\",\"level\":\"A1\",\"capacity\":10,"
        + "\"date\":\"2023-05-01T12:00:00\",\"usersInChitchat\":[\"testUser2\"],"
        + "\"avatarUrl\":null,\"conferenceLink\":null}";

    this.mockMvc.perform(
            get("/api/v1/chitchats/all/1")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.messages", "t.users":"username"="testUser3", "t.chitchats":"id"=2.
   */
  @Test
  @WithMockUser("testUserUser")
  void getChitchatAllMessagesTest() throws Exception {

    String expectedResult = "[{"
        + "\"id\":1,"
        + "\"authorName\":\"testUser3\","
        + "\"chitchatId\":2,"
        + "\"message\":\"Hello test message!\","
        + "\"createdTime\":\"2023-05-07T18:15:01.002\","
        + "\"subscriptionType\":\"CHAT\""
        + "}]";

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/2")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.message_users":"read_status", "t.users":"username".
   */
  @Test
  @WithMockUser("testUser2")
  void getTotalCountUnreadUserMessagesTestStatus() throws Exception {

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/unread_count")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isOk())
        .andExpect(content().string("{\"value\":1}"));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.message_users":"read_status", "t.chitchats":"id", "t.users":"username"="testUser1".
   */
  @Test
  @WithMockUser("testUser1")
  void getAllUnreadUserChitchatsTest() throws Exception {

    String expectedResult = "[{\"chitchatId\":2,\"unreadCount\":1}]";

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/unread_chitchats")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * This method does not check the sending of messages by email. Uses the following lines from the
   * H2 in memory database tables(t) created by the script in the folder:
   * test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser2", "t.languages":"id"="en", "t.categories":"id"=2,
   * "t.chitchat_users":("chitchat_id", "user_id"=1), "t.reminder_data":"chitchat_id",
   * "t.reminder_emails":("data_id", "email"), "t.chitchats".
   */
  @Test
  @WithMockUser("testUser2")
  void addChitchatTest() throws Exception {

    String testChitchat = "{"
        + "    \"chatHeader\":\"testChat\","
        + "    \"categoryId\":2,"
        + "    \"description\":\"testing\","
        + "    \"languageId\":\"en\","
        + "    \"level\":\"B1\","
        + "    \"capacity\":4,"
        + "    \"date\":\"2023-05-27T12:16:29.834\""
        + "    }";

    Optional<Translation> optional = Optional.of(
        new Translation(1, "title_reminder", Locale.US, "message"));
    Mockito.when(translationRepo.findByMessageKeyAndLocale(any(), any())).thenReturn(optional);

    this.mockMvc.perform(
            post("/api/v1/chitchats")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(testChitchat))
        .andDo(print())
        .andExpect(status().isOk());
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.message_users":("messages_id", "user_id"=1, "read_status"), "t.messages": ("id",
   * "chitchat_id"=1, "created_time"), "t.users":"username"="testUser2".
   */
  @Test
  @WithMockUser("testUser2")
  void markReadMessagesOfChitchatTest(@Autowired MessageUsersRepo messageUsersRepo)
      throws Exception {

    this.mockMvc.perform(
            put("/api/v1/chitchats/chat_messages/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .param("date", "\"2023-05-07T18:15:01.002\"")
        )
        .andDo(print())
        .andExpect(status().isOk());

    boolean actual = messageUsersRepo.findById(new MessageUsersKey(3L, 1L))
        .orElseThrow().getReadStatus();
    boolean expected = true;

    assertEquals(expected, actual);
  }


  /**
   * This method does not check the sending of messages by email. Uses the following lines from the
   * H2 in memory database tables(t) created by the script in the folder:
   * test/resources/database-test.
   *
   * <p>"t.chitchats":"id"=3, "t.users":"username"="testUser3", "t.chitchat_users":("chitchat_id"=3,
   * "user_id"=3), "t.reminder_data":"chitchat_id"=3, "t.reminder_emails":("data_id"=3, "email").
   */
  @Test
  @WithMockUser("testUser3")
  void addUserToChitchatTest() throws Exception {

    Optional<Translation> optional = Optional.of(
        new Translation(1, "title_reminder",
            Locale.US, "message"));
    Mockito.when(translationRepo.findByMessageKeyAndLocale(any(), any())).thenReturn(optional);

    this.mockMvc.perform(
            put("/api/v1/chitchats/3")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.chitchats":"id"=4, "t.users":"username"="testUser1", "t.chitchat_users":("chitchat_id"=4,
   * "user_id"=2), "t.reminder_data":"chitchat_id"=4, "t.reminder_emails":("data_id"=4, "email"),
   */
  @Test
  @WithMockUser("testUser1")
  void removeUserFromChitchatTest() throws Exception {

    this.mockMvc.perform(
            put("/api/v1/chitchats/remove_user/4")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.chitchats":"id"=4, "t.reminder_data":"chitchat_id"=4, "t.users":"username"="testUser2".
   */
  @Test
  @WithMockUser("testUser2")
  void addChitchatLinkTest() throws Exception {

    String simpleDataDto = "{\"value\":\"https://1.203\"}";

    this.mockMvc.perform(
            put("/api/v1/chitchats/link/4")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(simpleDataDto))
        .andDo(print())
        .andExpect(status().isOk());
  }

}

