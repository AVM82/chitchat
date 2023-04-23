package com.group.chitchat.repository;

import com.group.chitchat.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
  Optional<Role> findRoleByName(String roleName);
}
