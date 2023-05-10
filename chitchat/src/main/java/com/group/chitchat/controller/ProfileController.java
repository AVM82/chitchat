package com.group.chitchat.controller;

import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.UserForEditDto;
import com.group.chitchat.model.dto.UserForResponseDto;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.profile.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;
  private final LocaleResolverConfig localeResolverConfig;

  /**
   * Main data for profile.
   */
  @GetMapping("/main")
  public ResponseEntity<UserForResponseDto> getProfile(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);

    return profileService.getProfile(requestHeader.getUserPrincipal().getName());
  }

  /**
   * Data for changing.
   */
  @GetMapping("/details")
  public ResponseEntity<UserForResponseDto> getProfileDetails(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getProfileDetails(requestHeader.getUserPrincipal().getName());
  }

  @GetMapping("/my_chitchats")
  public ResponseEntity<List<ChitchatForResponseDto>> getUserCreatedChats(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getUserCreatedChats(requestHeader.getUserPrincipal().getName());
  }

  @GetMapping("/planned_chitchats")
  public ResponseEntity<List<ChitchatForResponseDto>> getChatsWithUser(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getChatsWithUser(requestHeader.getUserPrincipal().getName());
  }

  @GetMapping("/archive_chitchats")
  public ResponseEntity<List<ChitchatForResponseDto>> getArchiveChats(
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getArchiveChats(requestHeader.getUserPrincipal().getName());
  }

  /**
   * Update data of user.
   *
   * @param userDto       dto with data for changing.
   * @param requestHeader request.
   * @param response      Http servlet response.
   * @return response with new data of user.
   */
  @PutMapping()
  public ResponseEntity<UserForResponseDto> updateUserData(@RequestBody UserForEditDto userDto,
      HttpServletRequest requestHeader, HttpServletResponse response) {

    localeResolverConfig.setLocale(requestHeader, response, null);

    return profileService.updateProfile(requestHeader.getUserPrincipal().getName(), userDto);
  }

  /**
   * Save file with avatar of user to external storage and return url of saved file.
   *
   * @param file with avatar image.
   * @return url of avatar.
   */
  @PostMapping("/avatar")
  public ResponseEntity<String> uploadAvatar(@RequestParam(value = "avatar") MultipartFile file,
      HttpServletRequest requestHeader, HttpServletResponse response) {

    localeResolverConfig.setLocale(requestHeader, response, null);

    return profileService.uploadAvatar(requestHeader.getUserPrincipal().getName(), file);
  }

  /**
   * Return url of current user avatar from DB.
   *
   * @return url of user avatar.
   */
  @GetMapping("/avatar")
  public ResponseEntity<String> getAvatarUrl(HttpServletRequest requestHeader,
      HttpServletResponse response) {

    localeResolverConfig.setLocale(requestHeader, response, null);

    return profileService.getAvatarUrl(requestHeader.getUserPrincipal().getName());
  }
}
