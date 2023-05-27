package com.group.chitchat.repository;

import com.group.chitchat.model.Translation;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepo extends JpaRepository<Translation, String> {

  Optional<Translation> findByMessageKeyAndLocale(String key, Locale locale);
}
