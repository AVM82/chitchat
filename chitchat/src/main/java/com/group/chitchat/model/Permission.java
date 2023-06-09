package com.group.chitchat.model;

import com.group.chitchat.model.enums.PermissionEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * Stores users account levels that can share functionality through the use of the "HasAuthority"
 * authorization check. The default is FREE. Created for the development of functionality in the
 * future. To activate, you need to implement the logic of changing account levels.
 */
@Entity
@Getter
@Setter
@Table(name = "permissions")
@NoArgsConstructor
public class Permission {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  @Enumerated(EnumType.STRING)
  private PermissionEnum name;

  @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
  private Set<User> users;

  @Override
  public String toString() {
    return "Permission{"
        + "id=" + id
        + ", name=" + name
        + ", users=" + users
        + '}';
  }
}

