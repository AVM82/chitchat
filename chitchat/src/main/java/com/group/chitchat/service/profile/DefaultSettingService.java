package com.group.chitchat.service.profile;

import com.group.chitchat.model.Language;
import com.group.chitchat.model.Permission;
import com.group.chitchat.model.Role;
import com.group.chitchat.model.User;
import com.group.chitchat.model.UserData;
import com.group.chitchat.model.enums.PermissionEnum;
import com.group.chitchat.model.enums.RoleEnum;
import com.group.chitchat.repository.LanguageRepo;
import com.group.chitchat.repository.PermissionRepo;
import com.group.chitchat.repository.RoleRepo;
import java.util.HashSet;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class DefaultSettingService {

  private static final PermissionEnum DEFAULT_PERMISSION = PermissionEnum.FREE;
  private static final RoleEnum DEFAULT_ROLE = RoleEnum.USER;
  private final RoleRepo roleRepo;
  private final PermissionRepo permissionRepo;
  private final LanguageRepo languageRepo;

  /**
   * Add new role to current user and delete default role, if exist.
   *
   * @param user     current user.
   * @param roleName new role.
   */
  public void updateRole(User user, String roleName) {
    Role newRole = roleRepo.findRoleByName(RoleEnum.valueOf(roleName.toUpperCase())).orElseThrow();
    Role defaultRole = roleRepo.findRoleByName(DEFAULT_ROLE).orElseThrow();
    Set<Role> userRoles = user.getRoles();
    userRoles.add(newRole);
    userRoles.remove(defaultRole);
    log.info("Role {} successfully added to user {}", roleName, user.getUsername());
  }

  /**
   * Set default value of permission to current user.
   *
   * @param user current user.
   */
  public void setDefaultPermission(User user) {
    Permission defaultPermission = permissionRepo.findPermissionByName(DEFAULT_PERMISSION)
        .orElseThrow(() -> new NoSuchElementException("Permission does not exist"));
    defaultPermission.setUsers(new HashSet<>());
    defaultPermission.getUsers().add(user);
    user.getPermissions().add(defaultPermission);
  }

  /**
   * Set default value of role to current user.
   *
   * @param user current user.
   */
  public void setDefaultRole(User user) {
    Role defaultRole = roleRepo.findRoleByName(DEFAULT_ROLE)
        .orElseThrow(() -> new NoSuchElementException("Role does not exist"));
    defaultRole.setUsers(new HashSet<>());
    defaultRole.getUsers().add(user);
    user.getRoles().add(defaultRole);
  }

  /**
   * Set native language of user depending on the default transmitted value.
   *
   * @param user current user
   */
  public void setDefaultLanguage(User user) {
    String languageId = Locale.getDefault().getLanguage();
    Language language = languageRepo.findById(languageId).orElseThrow();
    user.getUserData().setNativeLanguage(language);
  }

  public void setUserData(User user) {
    UserData userData = user.getUserData();
    userData.setUser(user);
  }
}
