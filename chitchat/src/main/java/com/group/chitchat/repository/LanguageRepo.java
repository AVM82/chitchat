package com.group.chitchat.repository;

import com.group.chitchat.model.Language;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<Language, String> {

  Optional<Language> findByName(String name);

}
