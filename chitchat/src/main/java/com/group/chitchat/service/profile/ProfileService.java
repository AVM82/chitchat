package com.group.chitchat.service.profile;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.exception.UserNotFoundException;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.UserDto;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.UserDtoService;
import com.group.chitchat.service.chitchat.ChitchatDtoService;
import com.group.chitchat.service.internationalization.BundlesService;
import java.util.List;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

  private final BundlesService bundlesService;
  private final ChitchatRepo chitchatRepo;
  private final UserRepo userRepo;

  public ResponseEntity<UserDto> getProfile(Long userId) {
    User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    return ResponseEntity.ok(UserDtoService.userForProfile(user));
  }

  public ResponseEntity<UserDto> getForChangeProfileDetails(Long userId) {
    User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    return ResponseEntity.ok(UserDtoService.userForChange(user));
  }

  public ResponseEntity<List<ChitchatForResponseDto>> getUserCreatedChats(Long userId) {
    List<Chitchat> chitchats = chitchatRepo.findAllByAuthorId(userId)
        .orElseThrow(() -> new ChitchatsNotFoundException(bundlesService.getMessForLocale(
            "m.not_any_chat_created", Locale.getDefault())));

    return ResponseEntity.ok(chitchats.stream().map(ChitchatDtoService::getFromEntity).toList());
  }

  public ResponseEntity<List<ChitchatForResponseDto>> getChatsWithUser(Long userId) {
    User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

    List<Chitchat> chitchats = chitchatRepo.findByUsersInChitchatContaining(user)
        .orElseThrow(() -> new ChitchatsNotFoundException(bundlesService.getMessForLocale(
            "m.not_have_chats", Locale.getDefault())));

    return ResponseEntity.ok(chitchats.stream().map(ChitchatDtoService::getFromEntity).toList());

  }
}
