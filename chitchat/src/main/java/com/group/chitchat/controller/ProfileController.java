package com.group.chitchat.controller;


import com.group.chitchat.model.dto.ChitchatForResponseDto;
import com.group.chitchat.model.dto.UserDto;
import com.group.chitchat.service.internationalization.LocaleResolverConfig;
import com.group.chitchat.service.profile.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

  private final ProfileService profileService;
  private final LocaleResolverConfig localeResolverConfig;

  /**
   * Main data for profile.
   */
  @GetMapping("/main/{userId}")
  public ResponseEntity<UserDto> getProfile(@PathVariable("userId") Long userId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getProfile(userId);
  }

  /**
   * Data for changing
   */
  @GetMapping("/details/{userId}")
  public ResponseEntity<UserDto> getProfileDetails(@PathVariable("userId") Long userId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getForChangeProfileDetails(userId);
  }

  @GetMapping("/my_chitchats/{userId}")
  public ResponseEntity<List<ChitchatForResponseDto>> getUserCreatedChats(
      @PathVariable("userId") Long userId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getUserCreatedChats(userId);
  }

  @GetMapping("/planned_chitchats/{userId}")
  public ResponseEntity<List<ChitchatForResponseDto>> getChatsWithUser(
      @PathVariable("userId") Long userId,
      HttpServletRequest requestHeader, HttpServletResponse response) {
    localeResolverConfig.setLocale(requestHeader, response, null);
    return profileService.getChatsWithUser(userId);
  }
}
