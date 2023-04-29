package com.group.chitchat.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserOfChitchatDetails extends UserDetails {

  long getId();
}
