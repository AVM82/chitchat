package com.group.chitchat.service.chitchat;

import com.group.chitchat.model.Category;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.ForCreateChitchatDto;
import com.group.chitchat.repository.CategoryRepo;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.email.EmailService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChitchatService {

  private final ChitchatRepo chitchatRepo;
  private final UserRepo userRepo;
  private final LanguageRepo languageRepo;
  private final CategoryRepo categoryRepo;
  /**
   * Messages for sending email.
   */
  private static final String MESSAGE_CONFIRM_CREATE = "You created a new chitchat";
  private static final String MESSAGE_PARTICIPATION_CREATE = "You joined to chitchat";
  private final EmailService emailService;

  /**
   * Returns list of chitchats.
   *
   * @return list of chitchats.
   */
  public List<ChitchatForResponseDto> getAllChitchats() {
    return chitchatRepo.findAll().stream()
        .map(ChitchatDtoService::getFromEntity)
        .toList();
  }

  /**
   * Creating a new chitchat and saving into DB.
   *
   * @param chitchatDto Dto for create chitchat.
   * @return response with info of chitchat.
   */
  public ChitchatForResponseDto addChitchat(ForCreateChitchatDto chitchatDto, String authorName) {
    User author = userRepo.findByUsername(authorName)
        .orElseThrow(() -> new UsernameNotFoundException(authorName));

    //TODO add exceptions for two throws below

    Language language = languageRepo.findByName(chitchatDto.getLanguageName()).orElseThrow();
    Category category = categoryRepo.findByName(chitchatDto.getCategoryName()).orElseThrow();
    Chitchat chitchat = ChitchatDtoService.getFromDtoForCreate(
        chitchatDto, author, language, category);
    chitchat.getUsersInChitchat().add(author);

    chitchatRepo.save(chitchat);

    //sendEmail(chitchat, MESSAGE_CONFIRM_CREATE);

    return ChitchatDtoService.getFromEntity(chitchat);
  }

  /**
   * Sends a confirmation email.
   *
   * @param chitchat Entity with data.
   * @param message  Message for sending
   */
  private void sendEmail(Chitchat chitchat, String message) {
    emailService.sendEmail(
        chitchat.getAuthor().getEmail(),
        String.format("Chitchat: speak about %s", chitchat.getCategory()),
        message
    );
  }
}
