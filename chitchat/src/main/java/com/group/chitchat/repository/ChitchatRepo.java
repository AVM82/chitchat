package com.group.chitchat.repository;

import com.group.chitchat.model.Chitchat;
import com.group.chitchat.model.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ChitchatRepo extends JpaRepository<Chitchat, Long>,
    JpaSpecificationExecutor<Chitchat> {

  Page<Chitchat> findAll(Specification<Chitchat> specification, Pageable pageable);

  Optional<List<Chitchat>> findAllByAuthorId(Long authorId);

  Optional<List<Chitchat>> findByUsersInChitchatContaining(User user);
}
