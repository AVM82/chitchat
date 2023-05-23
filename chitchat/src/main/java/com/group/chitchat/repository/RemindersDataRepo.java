package com.group.chitchat.repository;

import com.group.chitchat.model.RemindersData;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RemindersDataRepo extends JpaRepository<RemindersData, Long> {

  Optional<List<RemindersData>> findAllByStartTimeBetweenAndRemindedIsFalse(
      LocalDateTime currentTime,
      LocalDateTime reminderTime);
}
