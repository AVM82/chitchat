package com.group.chitchat.service.email;

import com.group.chitchat.exception.NotValidTranslationKeyException;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.RemindersData;
import com.group.chitchat.repository.RemindersDataRepo;
import com.group.chitchat.repository.TranslationRepo;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReminderPlanner {

  private static final String MESSAGE = "mail_reminder";
  private static final String TITLE = "title_reminder";
  private final RemindersDataRepo repo;
  private final EmailService emailService;
  private final TranslationRepo translationRepo;

  /**
   * Save data to reminder table.
   */

  public void createReminderData(Chitchat chitchat) {
    Set<String> usersEmails = new HashSet<>();
    usersEmails.add(chitchat.getAuthor().getEmail());
    RemindersData data = RemindersData.builder()
        .startTime(chitchat.getDate())
        .emails(usersEmails)
        .locale(new Locale(chitchat.getLanguage().getCodeIso()))
        .reminded(false)
        .build();
    data.setChitchat(chitchat);
    chitchat.setRemindersData(data);
  }

  /**
   * After a specified period, it checks in the repository the records for which you need to send a
   * reminder.
   */
  @Scheduled(fixedRate = 600000)
  @Transactional
  public void sendReminder() {
    log.info("Start scheduled");
    LocalDateTime currentTime = LocalDateTime.now();
    LocalDateTime reminderTime = currentTime.plusMinutes(60);

    Optional<List<RemindersData>> remindersDataOptional = repo
        .findAllByStartTimeBetweenAndRemindedIsFalse(currentTime, reminderTime);

    if (remindersDataOptional.isPresent()) {
      for (RemindersData data : remindersDataOptional.get()) {

        data.setReminded(true);
        Locale locale = data.getLocale();
        String title = translationRepo.findByMessageKeyAndLocale(TITLE, locale)
            .orElseThrow(() -> new NotValidTranslationKeyException(TITLE)).getMessage();
        String message = translationRepo.findByMessageKeyAndLocale(MESSAGE, locale)
            .orElseThrow(() -> new NotValidTranslationKeyException(MESSAGE)).getMessage();

        for (String email : data.getEmails()) {
          emailService.sendEmail(email, title, String.format(
              message, ChronoUnit.MINUTES.between(currentTime, data.getStartTime()),
              data.getLink()));
        }
      }
    }
    log.info("Finish scheduled");
  }
}