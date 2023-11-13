package com.progi.WildTrack.repository;

import java.util.List;
import java.util.Optional;

import com.progi.WildTrack.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
      select t from Token t inner join Client u\s
      on t.client.client_name = u.client_name\s
      where u.client_name = :username and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByClient(String username);

    Optional<Token> findByToken(String token);
}
