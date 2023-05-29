package com.group.chitchat.controller;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.group.chitchat.ChitchatApplication;
import com.group.chitchat.model.MessageUsersKey;
import com.group.chitchat.repository.TranslationRepo;
import com.group.chitchat.service.email.ChitchatEmailService;
import com.group.chitchat.service.profile.StorageServiceS3Bucket;
import org.junit.jupiter.api.Test;
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
@SpringBootTest(classes = ProfileController.class)
@AutoConfigureMockMvc
@ComponentScan("com.group.chitchat")
public class ProfileControllerTest {

  @Autowired
  MockMvc mockMvc;


  @MockBean
  StorageServiceS3Bucket storageServiceS3Bucket;
  @MockBean
  ChitchatEmailService chitchatEmailService;
//  @MockBean
//  TranslationRepo translationRepo;

  /**
   * Checks the performance of the controller.
   */
  @Test
  void contextLoadsSmokeTest(@Autowired ProfileController controller) {
    assertThat(controller).isNotNull();
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser2",
   * "t.users_roles":("user_id"=1,"role_id"=1),
   * "t.roles":("id"=1, "name"="ADMIN"),
   * "t.user_data":("user_id"=1, "avatar"=null)
   */
  @Test
  @WithMockUser("testUser2")
  void getProfileTest() throws Exception {

    String expectedResult = "{"
        + "\"userName\":\"testUser2\","
        + "\"roles\":[\"ADMIN\"],"
        + "\"email\":\"useremail2@gmail.com\","
        + "\"avatar\":null,"
        + "\"nativeLanguage\":null,"
        + "\"firstname\":null,"
        + "\"lastname\":null,"
        + "\"gender\":null,"
        + "\"dob\":null}";

    this.mockMvc.perform(
            get("/api/v1/profile/main")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser1",
   * "t.users_roles":("user_id"=2,"role_id"=2),
   * "t.roles":("id"=2, "name"="USER"),
   * "t.user_data":("user_id"=2, "avatar" and other)
   */
  @Test
  @WithMockUser("testUser1")
  void getProfileDetailsTest() throws Exception {

    String expectedResult = "{"
        + "\"userName\":\"testUser1\","
        + "\"roles\":[\"USER\"],"
        + "\"email\":\"useremail1@gmail.com\","
        + "\"avatar\":null,"
        + "\"nativeLanguage\":\"english\","
        + "\"firstname\":\"UserUser\","
        + "\"lastname\":\"Tester\","
        + "\"gender\":\"FEMALE\","
        + "\"dob\":\"2023-05-01\""
        + "}";

    this.mockMvc.perform(
            get("/api/v1/profile/details")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser1",
   * "t.chitchats":("author_id"=2,"time").
   */
  @Test
  @WithMockUser("testUser1")
  void getUserCreatedChatsTest() throws Exception {

    String expectedResult = "[{"
        + "\"id\":2,"
        + "\"chatName\":\"lets go\","
        + "\"authorName\":\"testUser1\","
        + "\"categoryName\":\"Casual Conversation\","
        + "\"description\":\"speak with me\","
        + "\"languageName\":\"deutsch\","
        + "\"level\":\"NATIVE\","
        + "\"capacity\":2,"
        + "\"date\":\"2033-06-01T12:00:00\","
        + "\"usersInChitchat\":[\"testUser1\"],"
        + "\"avatarUrl\":null,"
        + "\"conferenceLink\":null"
        + "}]";

    this.mockMvc.perform(
            get("/api/v1/profile/my_chitchats")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser1",
   * "t.chitchat_user":("chitchat_id"=2, "user_id"=2),
   * "t.chitchat":("id"=2, "time"=....).
   */
  @Test
  @WithMockUser("testUser1")
  void getChatsWithUserTest() throws Exception {

    String expectedResult = "[{"
        + "\"id\":2,"
        + "\"chatName\":\"lets go\","
        + "\"authorName\":\"testUser1\","
        + "\"categoryName\":\"Casual Conversation\","
        + "\"description\":\"speak with me\","
        + "\"languageName\":\"deutsch\","
        + "\"level\":\"NATIVE\","
        + "\"capacity\":2,"
        + "\"date\":\"2033-06-01T12:00:00\","
        + "\"usersInChitchat\":[\"testUser1\"],"
        + "\"avatarUrl\":null,"
        + "\"conferenceLink\":null"
        + "}]";

    this.mockMvc.perform(
            get("/api/v1/profile/planned_chitchats")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }

  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser3",
   * "t.chitchat_user":("chitchat_id"=3, "user_id"=3),
   * "t.chitchat":("id"=3, "time"=....).
   */
  @Test
  @WithMockUser("testUser3")
  void getArchiveChatsTest() throws Exception {

    String expectedResult = "[{"
        + "\"id\":3,"
        + "\"chatName\":\"lets go testing\","
        + "\"authorName\":\"testUser3\","
        + "\"categoryName\":\"How beer can help you understand the theory of relativity\","
        + "\"description\":\"test this chat\","
        + "\"languageName\":\"english\","
        + "\"level\":\"A2\","
        + "\"capacity\":7,"
        + "\"date\":\"2023-05-21T10:10:00\","
        + "\"usersInChitchat\":[\"testUser3\"],"
        + "\"avatarUrl\":null,"
        + "\"conferenceLink\":null"
        + "}]";

    this.mockMvc.perform(
            get("/api/v1/profile/archive_chitchats")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }


  /**
   * Uses the following lines from the H2 in memory database tables(t) created by the script in the
   * folder: test/resources/database-test.
   *
   * <p>"t.users":"username"="testUser4",
   * "t.roles":("id"=4,"name"="OBSERVER"),
   * "t.user_data",
   * "t.languages":("id"="en", "name"="english"),
   */
  @Test
  @WithMockUser("testUser4")
  void updateUserDataTest() throws Exception {

    String userDto = "{"
        + "\"firstname\":\"MyUpdateName\","
        + "\"lastname\":\"MyUpdateLastName\","
        + "\"role\":\"OBSERVER\","
        + "\"avatar\":\"null\","
        + "\"nativeLanguage\":\"en\","
        + "\"dob\":\"2023-05-05\","
        + "\"gender\":\"FEMALE\""
        + "}";

    String expectedResult = "{"
        + "\"userName\":\"testUser4\","
        + "\"roles\":[\"OBSERVER\"],"
        + "\"email\":\"useremail4@gmail.com\","
        + "\"avatar\":\"null\","
        + "\"nativeLanguage\":\"english\","
        + "\"firstname\":\"MyUpdateName\","
        + "\"lastname\":\"MyUpdateLastName\","
        + "\"gender\":\"FEMALE\","
        + "\"dob\":\"2023-05-05\""
        + "}";

    this.mockMvc.perform(
            put("/api/v1/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(userDto))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string(expectedResult));
  }





}
