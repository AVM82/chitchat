package com.group.chitchat.repository;

import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.Language;
import com.group.chitchat.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChitchatRepo extends JpaRepository<Chitchat, Long> {

  List<Chitchat> findAllByLanguage(Language language);

  Optional<List<Chitchat>> findAllByAuthorId(Long id);

  Optional<List<Chitchat>> findByUsersInChitchatContaining(User user);
}
