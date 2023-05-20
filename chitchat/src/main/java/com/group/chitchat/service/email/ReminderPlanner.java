package com.group.chitchat.service.email;

import com.group.chitchat.model.RemindersData;
import com.group.chitchat.repository.RemindersDataRepo;
import com.group.chitchat.service.internationalization.BundlesService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Component
@RequiredArgsConstructor
public class ReminderPlanner {

  private static final String MESSAGE = "m.mail_reminder";
  private static final String TITLE = "m.title_reminder";
  private final RemindersDataRepo repo;
  private final BundlesService bundlesService;
  private final EmailService emailService;

  /**
   * After a specified period, it checks in the repository the records for which you need to send a
   * reminder.
   */
  @Scheduled(fixedRate = 300000)
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
        for (String email : data.getEmails()) {
          emailService.sendEmail(
              email,
              bundlesService.getMessForLocale(TITLE, Locale.getDefault()),
              String.format(
                  bundlesService.getMessForLocale(MESSAGE, Locale.getDefault()), data.getLink()));
          log.info("Finish scheduled");
        }
      }
    }
  }
}