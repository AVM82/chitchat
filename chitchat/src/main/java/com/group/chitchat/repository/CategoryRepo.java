package com.group.chitchat.repository;

import com.group.chitchat.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

  Optional<Category> findByName(String name);

}
