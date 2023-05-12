package com.group.chitchat.repository;

import com.group.chitchat.model.Permission;
import com.group.chitchat.model.enums.PermissionEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Long> {

  Optional<Permission> findPermissionByName(PermissionEnum name);

}
