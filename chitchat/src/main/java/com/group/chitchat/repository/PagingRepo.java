package com.group.chitchat.repository;

import com.group.chitchat.model.Chitchat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PagingRepo extends JpaRepository<Chitchat, Long>,
    JpaSpecificationExecutor<Chitchat> {

  Page<Chitchat> findAll(Specification<Chitchat> specification, Pageable pageable);
}
