package com.group.chitchat.repository;

import com.group.chitchat.model.Translation;
import java.util.Locale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranslationRepo extends JpaRepository<Translation, String> {

  String findByKeyAnd(String key, Locale locale);
}
