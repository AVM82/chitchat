package com.group.chitchat.service.email;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventService {

  private static final String CALENDAR_URL = "https://www.google.com/calendar/render";

  public static String generateCalendarLink(String chatName, String description,
      LocalDateTime time) {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    String eventStartDate = time.format(formatter);
    return CALENDAR_URL
        + "?action=TEMPLATE"
        + "&text="
        + URLEncoder.encode(chatName, StandardCharsets.UTF_8)
        + "&details="
        + URLEncoder.encode(description, StandardCharsets.UTF_8)
        + "&dates=" + eventStartDate;
  }
}
