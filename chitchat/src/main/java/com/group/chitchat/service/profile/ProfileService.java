package com.group.chitchat.service.profile;

import com.group.chitchat.exception.ChitchatsNotFoundException;
import com.group.chitchat.exception.UserNotFoundException;
import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import com.group.chitchat.model.dto.AvatarDto;
import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.UserForEditDto;
import com.group.chitchat.model.dto.UserForResponseDto;
import com.group.chitchat.repository.ChitchatRepo;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.RoleRepo;
import com.group.chitchat.repository.UserRepo;
import com.group.chitchat.service.chitchat.ChitchatDtoService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service that manages data of users profile.
 */
@Service
@RequiredArgsConstructor
public class ProfileService {

  private final ChitchatRepo chitchatRepo;
  private final UserRepo userRepo;
  private final LanguageRepo languageRepo;
  private final RoleRepo roleRepo;
  private final FileStorageService fileStorage;
  private final DefaultSettingService roleService;

  /**
   * Returns information of users profile.
   *
   * @param username of current user.
   * @return response with information of profile.
   */
  public UserForResponseDto getProfile(String username) {
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return UserDtoService.profileDtoFromUser(user);
  }

  /**
   * Returns detailed information of users profile.
   *
   * @param username of current user.
   * @return response with information of profile.
   */
  public UserForResponseDto getProfileDetails(String username) {
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    return UserDtoService.profileDetailsDtoFromUser(user);
  }

  /**
   * Returns all chitchats in which the user is the author.
   *
   * @param username of current user.
   * @return chitchats created by the user.
   */
  public List<ChitchatForResponseDto> getUserCreatedChats(String username) {
    Long authorId = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username)).getId();
    List<Chitchat> chitchats = chitchatRepo.findAllByAuthorId(authorId).orElseThrow();

    if (chitchats.isEmpty()) {
      throw new ChitchatsNotFoundException();
    } else {
      return chitchats.stream().filter(chitchat -> chitchat.getDate().isAfter(LocalDateTime.now()))
          .map(ChitchatDtoService::getFromEntity).toList();
    }
  }

  /**
   * Returns all chitchats in which the user is the member.
   *
   * @param username of current user.
   * @return all chitchats with user.
   */
  public List<ChitchatForResponseDto> getChatsWithUser(String username) {
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    List<Chitchat> chitchats = chitchatRepo.findByUsersInChitchatContaining(user).orElseThrow();

    if (chitchats.isEmpty()) {
      throw new ChitchatsNotFoundException();
    } else {
      return chitchats.stream().filter(chitchat -> chitchat.getDate().isAfter(LocalDateTime.now()))
          .map(ChitchatDtoService::getFromEntity).toList();
    }
  }

  /**
   * Returns archive chitchats of user.
   *
   * @param username of current user.
   * @return archive chitchats of user.
   */
  public List<ChitchatForResponseDto> getArchiveChats(String username) {
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));
    List<Chitchat> chitchats = chitchatRepo.findByUsersInChitchatContaining(user).orElseThrow();

    if (chitchats.isEmpty()) {
      throw new ChitchatsNotFoundException();
    } else {
      return chitchats.stream().filter(chitchat -> chitchat.getDate().isBefore(LocalDateTime.now()))
          .map(ChitchatDtoService::getFromEntity).toList();
    }
  }

  /**
   * Update data of user.
   *
   * @param username of current user.
   * @param userDto  dto with data for changing.
   * @return response with new data of user.
   */
  @Transactional
  public UserForResponseDto updateProfile(String username, UserForEditDto userDto) {
    User user = userRepo.findByUsername(username)
        .orElseThrow(() -> new UserNotFoundException(username));

    if (userDto.getRole() != null) {
      roleService.updateRole(user, userDto.getRole());
    }
    if (userDto.getAvatar() != null && !userDto.getAvatar().isEmpty()) {
      user.getUserData().setAvatar(userDto.getAvatar());
    }
    if (userDto.getFirstname() != null && !userDto.getFirstname().isEmpty()) {
      user.getUserData().setFirstName(userDto.getFirstname());
    }
    if (userDto.getLastname() != null && !userDto.getLastname().isEmpty()) {
      user.getUserData().setLastName(userDto.getLastname());
    }
    if (userDto.getGender() != null) {
      user.getUserData().setGender(userDto.getGender());
    }
    if (userDto.getDob() != null) {
      user.getUserData().setDob(userDto.getDob());
    }
    if (userDto.getNativeLanguage() != null && !userDto.getNativeLanguage().isEmpty()) {
      Language language = languageRepo.findById(userDto.getNativeLanguage()).orElseThrow();
      user.getUserData().setNativeLanguage(language);
    }
    return UserDtoService.profileDetailsDtoFromUser(user);
  }

  /**
   * Save file with avatar of user to external storage and return url of saved file.
   *
   * @param userName of current user.
   * @param file     file with users avatar.
   * @return url of avatar.
   */
  @Transactional
  public AvatarDto uploadAvatar(String userName, MultipartFile file) {
    User user = userRepo.findByUsername(userName)
        .orElseThrow(() -> new UserNotFoundException(userName));

    String oldAvatar = user.getUserData().getAvatar();
    String avatarUrl = fileStorage.saveFile(userName, file);
    user.getUserData().setAvatar(avatarUrl);

    if (oldAvatar != null) {
      fileStorage.deleteFile(oldAvatar);
    }
    return new AvatarDto(avatarUrl);
  }

  /**
   * Return url of current user avatar from DB.
   *
   * @param userName of current user
   * @return url of user avatar.
   */
  public AvatarDto getAvatarUrl(String userName) {
    User user = userRepo.findByUsername(userName)
        .orElseThrow(() -> new UserNotFoundException(userName));

    return new AvatarDto(user.getUserData().getAvatar());
  }
}
