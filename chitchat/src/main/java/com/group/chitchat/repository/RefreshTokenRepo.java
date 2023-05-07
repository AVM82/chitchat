package com.group.chitchat.repository;

import com.group.chitchat.model.RefreshToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

  Optional<RefreshToken> findRefreshTokenByTokenForRefresh
      (String tokenForRefresh);

  void deleteByTokenForRefresh(String entity);
}
