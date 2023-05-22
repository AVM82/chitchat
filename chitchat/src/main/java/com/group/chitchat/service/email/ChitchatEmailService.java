package com.group.chitchat.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class ChitchatEmailService implements EmailService {

  private final JavaMailSender mailSender;

  @Override
  public void sendEmail(String email, String title, String textMessage) {

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(email);
      helper.setSubject(title);
      helper.setText(textMessage);
    } catch (MessagingException e) {
      log.error("Failed to send email to {}", email, e);
      //TODO response to front
    }
    mailSender.send(message);
    log.info("Email to {} sent successfully", email);
  }


}
