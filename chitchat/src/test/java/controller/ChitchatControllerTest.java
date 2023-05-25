package controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.group.chitchat.controller.ChitchatController;
import com.group.chitchat.service.email.ChitchatEmailService;
import com.group.chitchat.service.profile.StorageServiceS3Bucket;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@SpringBootTest(classes = ChitchatController.class)
@AutoConfigureMockMvc
@ComponentScan("com.group.chitchat")
//@ComponentScan(basePackages = "com.group.chitchat")
//@EntityScan(basePackages = "com.group.chitchat.model")
//@EnableJpaRepositories(basePackages = "com.group.chitchat.repository")
class ChitchatControllerTest {

  @Autowired
  MockMvc mockMvc;


  @MockBean
  StorageServiceS3Bucket storageServiceS3Bucket;
  @MockBean
  ChitchatEmailService chitchatEmailService;
  @MockBean
  HttpServletRequest httpServletRequest;
  @MockBean
  HttpServletResponse httpServletResponse;


  @Test
  void contextLoadsSmokeTest(@Autowired ChitchatController controller) {
    assertThat(controller).isNotNull();
  }


  @Test
  @WithMockUser("testUser2")
  void getChitchatTest() throws Exception {

    String expectedResult = "{\"id\":2,\"chatName\":\"lets go\",\"authorName\":\"testUser3\","
        + "\"categoryName\":\"Casual Conversation\",\"description\":\"speak with me\","
        + "\"languageName\":\"deutsch\",\"level\":\"NATIVE\",\"capacity\":2,"
        + "\"date\":\"2023-06-01T12:00:00\",\"usersInChitchat\":[\"testUser1\"],"
        + "\"avatarUrl\":null,\"conferenceLink\":null}";

    this.mockMvc.perform(
            get("/api/v1/chitchats/2").contentType("application/json")).andDo(print())
        .andExpect(status().isOk()).andExpect(content().string(expectedResult));
  }


  @Test
  @WithMockUser("testUserUser")
  void getPublicChitchatTest() throws Exception {

    String expectedResult = "{\"id\":1,\"chatName\":\"test header\",\"authorName\":\"testUser2\","
        + "\"categoryName\":\"Computer Science and IT\",\"description\":\"test description\","
        + "\"languageName\":\"english\",\"level\":\"A1\",\"capacity\":10,"
        + "\"date\":\"2023-05-01T12:00:00\",\"usersInChitchat\":[\"testUser2\"],"
        + "\"avatarUrl\":null,\"conferenceLink\":null}";

    this.mockMvc.perform(
            get("/api/v1/chitchats/all/1").contentType("application/json")).andDo(print())
        .andExpect(status().isOk()).andExpect(content().string(expectedResult));
  }


  @Test
  @WithMockUser("testUserUser")
  void getChitchatAllMessagesTestStatus() throws Exception {

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/7"))
        .andDo(print()).andExpect(status().isOk());
  }


  @Test
  @WithMockUser("testUserUser")
  void getUnreadUserMessagesTestStatus() throws Exception {

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/7"))
        .andDo(print()).andExpect(status().isOk());
  }


  @Test
  @WithMockUser("testUserUser")
  void getTotalCountUnreadUserMessagesTestStatus() throws Exception {

    this.mockMvc.perform(
            get("/api/v1/chitchats/chat_messages/unread_count"))
        .andDo(print()).andExpect(status().isOk());
  }


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

    this.mockMvc.perform(
            post("/api/v1/chitchats")
                .contentType(MediaType.APPLICATION_JSON)
                .content(testChitchat))
        .andDo(print())
        .andExpect(status().is2xxSuccessful());
  }


}

